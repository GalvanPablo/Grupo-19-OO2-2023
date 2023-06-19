import React from 'react';

// Manejo de Datos globales
import { useSelector } from 'react-redux';

// Navegacion
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'

// Layout
import { Navbar, Sidebar } from './layout'

// Paginas
import { Auth, Dashboard, NotFound, DispositivosEstacionamiento, DispositivoRiego, EventosDispositivo } from './pages'

import './App.css';
import DispositivosRiego from './pages/soluciones/DispositivoRiego';

function App() {
    const activeMenu = useSelector(state => state.menu.activeMenu);
    const isAuthenticated = useSelector(state => state.auth.isAuthenticated);


    // localStorage.clear();
    // sessionStorage.clear();

    return (
        <BrowserRouter>
            {isAuthenticated ? (
                <div className="flex relative dark:bg-main-dark-bg">
                    <div className={activeMenu ? 'w-72 fixed sidebar dark:bg-main-dark-bg bg-white' : 'w-0 dark:bg-secondary-dark-bg'}>
                        <Sidebar />
                    </div>

                    <div className={`dark:bg-main-bg bg-main-bg min-h-screen w-full ${activeMenu ? 'md:ml-72' : 'flex-2'}`}>
                        <div className='fixed md:static bg-main-bg dark:bg-main-dark-bg navbar w-full'>
                            <Navbar />
                        </div>

                        <div>
                            <Routes>
                                {/* Dashboard */}
                                <Route path='/auth' element={<Navigate to="/dashboard" />} />
                                <Route path='/' element={<Navigate to="/dashboard" />} />
                                <Route path='/dashboard' element={<Dashboard />} />

                                {/* Soluciones IoT */}
                                <Route path='/estacionamiento' element={<DispositivosEstacionamiento />} />
                                <Route path='/riego' element={<DispositivosRiego/>} />


                                {/* Eventos */}
                                <Route path='/eventos/dispositivo/:idDispositivo' element={<EventosDispositivo />} />

                                <Route path='*' element={<NotFound />} />
                            </Routes>
                        </div>
                    </div>
                </div>
            ) : (
                <Routes>
                    <Route path='/auth' element={<Auth />} />
                    <Route path='*' element={<Navigate to="/auth" />} />
                </Routes>
            )}
        </BrowserRouter>
    );
}

export default App;
