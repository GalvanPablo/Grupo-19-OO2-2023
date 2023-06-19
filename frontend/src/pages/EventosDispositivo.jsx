import React, { useEffect } from 'react'
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';

import { faCalendarCheck } from '@fortawesome/free-solid-svg-icons';

import { Header } from './../components';
import { API_EVENTO } from './../data/api';

const EventosDispositivo = () => {
    const { idDispositivo } = useParams();
    const [eventosByDispositivo, setEventosByDispositivo] = React.useState([])

    const token = useSelector((state) => state.auth.token);

    useEffect(() => {
        fetch(API_EVENTO.GET_BY_ID_DISPOSITIVO(idDispositivo), {
            method: 'GET', // Método de la solicitud (puede ser GET, POST, PUT, DELETE, etc.)
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json' // Tipo de contenido de la solicitud (puede ser application/json u otro)
            },
        })
            .then(response => response.json())
            .then(data => setEventosByDispositivo(data));
    }, [idDispositivo]);

    return (
        <div className='m-2 mt-16 md:m-10  p-2 md:p-10 bg-white rounded-3xl'>
            <Header category='Eventos' title={`Dispositivo - ID ${idDispositivo}`} icon={faCalendarCheck} />
            {eventosByDispositivo.length === 0 ? (
                <div className='text-center text-gray-500 text-xl'>No hay eventos registrados</div>
            ) : (
                <table className="w-full my-3 border-1">
                    <thead>
                        <tr className='bg-slate-300'>
                            <th className='p-3 text-sm font-semibold text-left'>Id</th>
                            <th className='p-3 text-sm font-semibold text-left'>Descripción</th>
                            <th className='p-3 text-sm font-semibold text-left'>Medición</th>
                            <th className='p-3 text-sm font-semibold text-left'>Fecha y hora de registro</th>
                        </tr>
                    </thead>
                    <tbody>
                        {eventosByDispositivo.map((item, index) => (
                            <tr key={index} className={index % 2 !== 0 ? 'bg-slate-100' : ''}>
                                <td className='p-3 text-sm text-gray-700'>{item.idEvento}</td>
                                <td className='p-3 text-sm text-gray-700'>{item.descripcion}</td>
                                <td className='p-3 text-sm text-gray-700'>{item.idMedicion}</td>
                                <td className='p-3 text-sm text-gray-700'>{(new Date(item.fechaHoraRegistro.toString())).toLocaleString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    )
}

export default EventosDispositivo