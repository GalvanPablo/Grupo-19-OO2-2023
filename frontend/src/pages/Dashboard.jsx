import React, { useState, useEffect } from 'react'
import { API_ESTACIONAMIENTO, API_RIEGO } from '../data/api'
import { useSelector } from 'react-redux';

import { Header } from '../components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCar, faDroplet } from '@fortawesome/free-solid-svg-icons'

const Dashboard = () => {
    const token = useSelector((state) => state.auth.token); // Extrae el token del usuario del store de redux

    const [cantPlazasEstacionamiento, setCantPlazasEstacionamiento] = useState(0)
    const [cantPlazasOcupadasEstacionamiento, setCantPlazasOcupadasEstacionamiento] = useState(0)
    const obtenerDataEstacionamiento = () => {
        fetch(API_ESTACIONAMIENTO.GET_ALL, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.ok ? response.json() : null)
            .then(data => setCantPlazasEstacionamiento(data ? (data.map((item) => item.plazas.length)).reduce((a, b) => a + b, 0) : 0))
            .catch(error => {
                console.error(error);
            });

        fetch(API_ESTACIONAMIENTO.GET_LAST_STATE, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.ok ? response.json() : null)
            .then(data => setCantPlazasOcupadasEstacionamiento(data ? data.filter((item) => item.ocupado).length : 0))
            .catch(error => {
                console.error(error);
            });

    }

    const [cantDispositivosDeRiego, setCantDispositivosDeRiego] = useState(0)
    const [cantDispositivosDeRiegoActivos, setCantDispositivosDeRiegoActivos] = useState(0)
    const obtenerDataDispositivosDeRiego = () => {
        fetch(API_RIEGO.GET_ALL, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.ok ? response.json() : null)
            .then(data => {
                if (data) {
                    setCantDispositivosDeRiego(data.length)
                    setCantDispositivosDeRiegoActivos(data.filter((item) => item.activo).length)
                }
            })
            .catch(error => {
                console.error(error);
            });
    }

    useEffect(() => {
        obtenerDataEstacionamiento()
        obtenerDataDispositivosDeRiego()
        const interval = setInterval(() => {
            obtenerDataEstacionamiento()
            obtenerDataDispositivosDeRiego()
        }, 10000); // Cada 10 segundos
        return () => clearInterval(interval);
    }, [])

    const desarrolladores = [
        {
            img: 'https://avatars.githubusercontent.com/u/89715168?v=4',
            nombre: 'Andres Cupo',
            github: 'https://github.com/Suhiang98'
        },
        {
            img: 'https://avatars.githubusercontent.com/u/103456849?v=4',
            nombre: 'Pablo Galvan',
            github: 'https://github.com/GalvanPablo'
        },
        {
            img: 'https://avatars.githubusercontent.com/u/129111003?v=4',
            nombre: 'Sebastian Maroni',
            github: 'https://github.com/sebastianmarioni'
        }
    ]

    const RenderCard = ({ icon, text, value, colorBase, colorBgIcon }) => (
        <div className={`flex flex-col justify-center items-center px-10 py-8 rounded-xl gap-2 hover:drop-shadow-xl`} style={{ backgroundColor: colorBase }}>
            <FontAwesomeIcon icon={icon} className={`mb-2 p-5 rounded-full text-4xl`} style={{ backgroundColor: colorBgIcon }} />
            <span className='text-2xl font-bold'>{value}</span>
            <span className='text-xl font-semibold text-gray-800'>{text}</span>
        </div>
    );

    return (
        <div className='mt-4 mx-10'>
            <div className='p-4 bg-white rounded-3xl'>
                <Header title='Dashboard' category='Smart City orientado al predio de la UNLa' />
                <div className='flex flex-wrap lg:flex-nowrap justify-items-start gap-5 mb-14'>

                    <RenderCard
                        icon={faCar}
                        text='Plazas Ocupadas'
                        value={(cantPlazasEstacionamiento > 0 ? Math.round((cantPlazasOcupadasEstacionamiento / cantPlazasEstacionamiento) * 100) : 0) + '%'}
                        colorBase='#A8DADC'
                        colorBgIcon='#6DC2C5'
                    />

                    <RenderCard
                        icon={faDroplet}
                        text='Dispositivos regando'
                        value={cantDispositivosDeRiegoActivos + '/' + cantDispositivosDeRiego}
                        colorBase='#B1E3A0'
                        colorBgIcon='#8AD572'
                    />


                </div>

                <div className='flex flex-row gap-20'>

                    <div className='flex flex-col gap-2'>
                        <h2 className='text-xl font-semibold'>Desarrolladores</h2>
                        <ul className='flex flex-row gap-2'>
                            {desarrolladores.map((item, index) => (
                                <li key={index}>
                                    <a href={item.github} target='_blank' rel='noreferrer' className='hover:drop-shadow-xl'>
                                        <div className='w-[100px] rounded-full overflow-hidden'>
                                            <img src={item.img} alt={item.nombre} />
                                        </div>
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </div>

                    <div className='flex flex-col gap-2'>
                        <h2 className='text-xl font-semibold'>Repositiorio</h2>
                        <div className='w-[100px] rounded-full overflow-hidden'>
                            <a href="https://github.com/GalvanPablo/Grupo-19-OO2-2023" target='_blank' rel='noreferrer' className='hover:drop-shadow-xl'>
                                <img src="https://cdn4.iconfinder.com/data/icons/iconsimple-logotypes/512/github-512.png" alt="repositorio github" />
                            </a>
                        </div>
                    </div>

                    <div className='flex flex-col gap-2'>
                        <h2 className='text-xl font-semibold'>Universidad</h2>
                        <div className='w-[100px] rounded-full overflow-hidden'>
                            <a href="http://www.unla.edu.ar/" target='_blank' rel='noreferrer' className='hover:drop-shadow-xl'>
                                <img src="data:image/png;base64,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" alt="Universidad Nacional de Lanús" />
                            </a>
                        </div>
                    </div>


                </div>

            </div>
        </div>
    )
}

export default Dashboard