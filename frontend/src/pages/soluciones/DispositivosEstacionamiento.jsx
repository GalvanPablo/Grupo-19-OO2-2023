import React, { useState, useEffect } from 'react'
import { Header, Modal } from './../../components';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';

// Componentes usados para el formulario
import { MultiselectNumber } from './../../components';

// Links de la API
import { API_ESTACIONAMIENTO, API_ZONAS } from '../../data/api';

// Iconos
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCar, faPlus, faPen, faTrash, faCalendarCheck } from '@fortawesome/free-solid-svg-icons';

// Carteles de notificaciones
import { Report } from 'notiflix/build/notiflix-report-aio';
import { Confirm } from 'notiflix/build/notiflix-confirm-aio';


const DispositivosEstacionamiento = () => {
    const { role } = useSelector((state) => state.auth); // Extrae el rol del usuario del store de redux
    const token = useSelector((state) => state.auth.token); // Extrae el token del usuario del store de redux

    useEffect(() => {
        console.log(token);
    }, [token])

    const tienePermiso = role === 'ADMIN'; // Si el rol es ADMIN, se le permite editar y eliminar

    const [zonas, setZonas] = React.useState([]); // Donde se guarda las zonas traidas de la api
    const cargarZonas = () => {
        fetch(API_ZONAS.GET_ALL, {
                method: 'GET', // Método de la solicitud (puede ser GET, POST, PUT, DELETE, etc.)
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json' // Tipo de contenido de la solicitud (puede ser application/json u otro)
                },
            })
            .then(response => response.json())
            .then(data => setZonas(data))
            .catch(error => console.log(error))
    }

    const [dispositivos, setDispositivos] = React.useState([]); // Donde se guarda los dispositivos traidos de la api
    const cargarDispositivos = () => {
        fetch(API_ESTACIONAMIENTO.GET_ALL, {
                method: 'GET', // Método de la solicitud (puede ser GET, POST, PUT, DELETE, etc.)
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json' // Tipo de contenido de la solicitud (puede ser application/json u otro)
                },
            })
            .then(response => response.json())
            .then(data => setDispositivos(data))
            .catch(error => Report.failure(
                'Error',
                'No se pudo cargar los dispositivos de estacionamiento',
                'Aceptar'
            ))
    }

    // Cuando se llega a la pagina, se cargan los dispositivos y las zonas
    useEffect(() => {
        cargarDispositivos();
        cargarZonas();
    }, [])

    // Valores locales para edicion y creacion de dispositivos
    const [id, setId] = useState(0);
    const [nombre, setNombre] = useState('');
    const [zona, setZona] = useState(null);
    const [plazas, setPlazas] = useState([]);

    // Funcion para guardar el nuevi dispositivo o editar uno existente
    const handleGuardar = () => {
        if (nombre.trim().length === 0 || zona == null || plazas.length === 0) {
            Report.warning(
                'Datos incompletos',
                'Por favor complete todos los campos',
                'Aceptar'
            );
            return;
        }
        const data = {
            nombre: nombre,
            zona: zona,
            plazas: plazas
        }

        if (id === 0) {
            // NUEVO DISPOSITIVO
            fetch(API_ESTACIONAMIENTO.CREATE, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
            })
                .then(response => {
                    if (!response.ok) {
                        response.text().then(text => {
                            Report.failure(
                                'ERROR ' + response.status,
                                text,
                                'Aceptar'
                            );
                        });
                    } else {
                        response.json()
                        Report.success(
                            'Dispositivo creado',
                            'Se ha creado el dispositivo correctamente',
                            'Aceptar'
                        );
                        cargarDispositivos();
                    }
                })
        } else {
            // EDITAR DISPOSITIVO
            fetch(API_ESTACIONAMIENTO.UPDATE(id), {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
            })
                .then(response => {
                    if (!response.ok) {
                        response.text().then(text => {
                            Report.failure(
                                'ERROR ' + response.status,
                                text,
                                'Aceptar'
                            );
                        });
                    } else {
                        response.json()
                        Report.success(
                            'Dispositivo editado',
                            'Se ha actualizado el dispositivo correctamente',
                            'Aceptar'
                        );
                        cargarDispositivos();
                    }
                })
        }
        setModalOpen(false);
    }

    // Funcion para eliminar un dispositivo
    const handleEliminar = (id, nombre) => {
        Confirm.show(
            '¿Desea eliminar este dispositivo? ',
            id + '. ' + nombre,
            'Confirmar',
            'Cancelar',
            () => { // Cuando se confirma la eliminacion, se envia la peticion a la api
                fetch(API_ESTACIONAMIENTO.DELETE(id), {
                    method: 'PUT',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                })
                    .then(response => { // Analizo la respuesta
                        if (!response.ok) { // Si la respuesta no es 200 OK, muestro el error
                            response.text().then(text => { // Convierto la respuesta a texto
                                Report.failure(
                                    'ERROR ' + response.status,
                                    text,
                                    'Aceptar'
                                );
                            });
                        } else { // Si la respuesta es 200 OK, muestro el mensaje de exito y recargo la lista de dispositivos
                            response.text().then(text => {
                                Report.success(
                                    'Dispositivo eliminado',
                                    text,
                                    'Aceptar'
                                );
                            });
                            cargarDispositivos(); // Recargo la lista de dispositivos
                        }
                    })
            }
        );
    }

    // Manejo del modal
    const [modalOpen, setModalOpen] = useState(false); // Visibilidad del modal
    const [desplegoZona, setDesplegoZona] = useState(false); // Para que cuando se despliege el listado de zonas, no permita seleccionar la 1era opcion que es "Seleccione una zona" (invalida)

    return (
        <div className='m-2 mt-16 md:m-10  p-2 md:p-10 bg-white rounded-3xl'>
            <Header category='Dispositivos' title='Estacionamiento' icon={faCar} />
            <div>
                {/* Si se tiene el rol de administrador, muestro el boton 'Nuevo' */}
                {role === 'ADMIN' && (
                    <button type="button"
                        className='flex items-center gap-2 px-4 py-2 mt-4 text-sm font-medium text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75'
                        onClick={() => {
                            // Reinicio los datos del formulario
                            setId(0);
                            setNombre('');
                            setZona(null);
                            setPlazas([]);

                            setDesplegoZona(false); // Reinicio el select de zonas
                            setModalOpen(true); // Abro el modal
                        }}
                    >
                        <FontAwesomeIcon icon={faPlus} /> Nuevo
                    </button>
                )}
                {dispositivos.length > 0 ? (
                    <table className="w-full my-3 border-1">
                        <thead>
                            <tr className='bg-slate-300'>
                                <th className='p-3 text-sm font-semibold text-left'>Id</th>
                                <th className='p-3 text-sm font-semibold text-left'>Nombre</th>
                                <th className='p-3 text-sm font-semibold text-left'>Zona</th>
                                <th className='p-3 text-sm font-semibold text-left'>Plazas</th>
                                <th className='p-3 text-sm font-semibold text-left w-[75px]'>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            {/* Recorro el listado de dispositivos y los muestro */}
                            {dispositivos.map((item, index) => (
                                <tr key={index} className={index % 2 !== 0 ? 'bg-slate-100' : ''}>
                                    {/* Atributos del dispositivo */}
                                    <td className='p-3 text-sm text-gray-700'>{item.idDispositivo}</td>
                                    <td className='p-3 text-sm text-gray-700'>{item.nombre}</td>
                                    <td className='p-3 text-sm text-gray-700'>{item.zona.nombre}</td>

                                    <td className='p-3 text-sm text-gray-700 w-400'>
                                        {item.plazas.map((plaza) => (
                                            <span className='bg-zinc-200 inline-block mx-1 rounded-xl w-7 h-5 text-center' key={plaza}>{plaza} </span>
                                        ))}
                                    </td>

                                    {/* Acciones */}
                                    <td className='flex justify-center py-2 gap-4'>
                                        {/* Ver eventos */}
                                        <Link to={`/eventos/dispositivo/${item.idDispositivo}`}>
                                            <FontAwesomeIcon icon={faCalendarCheck} className='text-green-800' />
                                        </Link>

                                        {/* Si tiene los permisos */}
                                        {role === 'ADMIN' && (
                                            <>
                                                {/* Editar */}
                                                <button type="button"
                                                    onClick={() => {
                                                        setId(item.idDispositivo);
                                                        setNombre(item.nombre);
                                                        setZona(item.zona);
                                                        setPlazas(item.plazas);

                                                        setModalOpen(true);
                                                    }}
                                                >
                                                    <FontAwesomeIcon icon={faPen} className='text-slate-600' />
                                                </button>

                                                {/* Eliminar */}
                                                <button type="button"
                                                    onClick={() => {
                                                        handleEliminar(item.idDispositivo, item.nombre)
                                                    }}
                                                >
                                                    <FontAwesomeIcon icon={faTrash} className='text-red-500' />
                                                </button>
                                            </>
                                        )}
                                    </td>

                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <div className='text-center text-gray-500 text-xl'>No hay dispositivos registrados</div>
                )}

                {/* Modal para editar o crear un nuevo dispositivo */}
                <Modal isOpen={modalOpen} width={'500px'}>
                    <h2 className="text-xl font-bold mb-4">
                        {id === 0 ? 'Nuevo Dispositivo' : `Editar Dispositivo - ID ${id}`}
                    </h2>

                    <div className='flex flex-col gap-2 my-5'>
                        {/* Input NOMBRE */}
                        <div>
                            <label htmlFor="first-name" className="block text-sm font-medium leading-6 text-gray-900">
                                Nombre
                            </label>
                            <div className="mt-2">
                                <input type="text" name="nombre" id="nombre"
                                    className="block w-full p-2 rounded-md border-1"
                                    placeholder='Nombre del dispositivo'
                                    onChange={(e) => setNombre(e.target.value)}
                                    value={nombre}
                                />
                            </div>
                        </div>

                        {/* Input ZONA */}
                        <div>
                            <label htmlFor="first-name" className="block text-sm font-medium leading-6 text-gray-900">
                                Zona
                            </label>
                            <div className="mt-2">
                                <select name="zona" id="zona" className="block w-full p-2 rounded-md border-1"
                                    onClick={() => setDesplegoZona(true)}
                                    onChange={(e) => setZona(zonas.find(zona => zona.idZona === parseInt(e.target.value)))}
                                    value={zona?.idZona}
                                >
                                    <option value="0" disabled={desplegoZona}>Seleccione una zona</option> {/* Si se despliega la lista, no permito seleccionar la 1era opcion que es "Seleccione una zona" */}
                                    {/* Mapeo de el listado zonas, para mostrarlo en el select */}
                                    {zonas.map((zona) => (
                                        <option value={zona.idZona} key={zona.idZona}>{zona.nombre}</option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        {/* Input PLAZAS */}
                        <div>
                            <label htmlFor="first-name" className="block text-sm font-medium leading-6 text-gray-900">Plazas</label>
                            <MultiselectNumber value={plazas} onChange={setPlazas} placeholder='Ingrese una plaza y presione enter' />
                        </div>


                    </div>

                    {/* Botones de GUARDAR y CANCELAR */}
                    <div className='flex gap-3 justify-end'>
                        <button
                            className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
                            onClick={() => handleGuardar()}
                        >
                            Guardar
                        </button>

                        <button
                            className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
                            onClick={() => setModalOpen(false)}
                        >
                            Cancelar
                        </button>
                    </div>
                </Modal>
            </div>
        </div>
    )
}

export default DispositivosEstacionamiento