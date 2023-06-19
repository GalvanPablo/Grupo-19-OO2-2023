import React from 'react'

import { useDispatch } from 'react-redux'

import { login } from './../store/actions/auth.action'

import { Report } from 'notiflix/build/notiflix-report-aio';

const AuthForm = () => {
    const dispatch = useDispatch();

    const [email, setEmail] = React.useState('');
    const [passwd, setPasswd] = React.useState('');

    return (
        <form action="#">
            <div className='mb-3'>
                <label htmlFor="email"className='font-medium mb-2 flex'>Email</label>
                <input type="email" name="" id="email" placeholder='Ingresa tu email'
                    className='w-full border rounded-md bg-transparent border-gray-400 p-3'
                    onChange={e => setEmail(e.target.value)}
                    required
                />
            </div>
            <div className='mb-3'>
                <label htmlFor="passwd" className='font-medium mb-2 flex'>Contraseña</label>
                <input type="password" name="" id="passwd" placeholder='Ingresa tu contraseña'
                    className='w-full border rounded-md bg-transparent border-gray-400 p-3'
                    onChange={e => setPasswd(e.target.value)}
                    required
                />
            </div>
            {/* <div className='flex justify-between mb-6'>
                <label htmlFor="recordar">
                    <input type="checkbox" name="recordar" id="recordar" />
                    <span className='ml-2'>
                        Recordarme
                    </span>
                </label>

                <span>¿Perdiste tu contraseña?</span>
            </div> */}

            <button
                type='submit'
                className='block bg-blue-700 hover:bg-blue-800 text-white w-full py-2 px-8 rounded'
                onClick={e => {
                    e.preventDefault();

                    if(email.trim().length === 0) {
                        Report.failure('Error', 'El email es requerido');
                        return;
                    }

                    if(passwd.trim().length === 0){
                        Report.failure('Error', 'La contraseña es requerida');
                        return;
                    }

                    dispatch(login(email, passwd));
                }}
            >
                Iniciar sesión
            </button>
            {/* <div className='mt-4 text-center'>
                No tienes una cuenta? <button className='text-blue-700'>Regístrate</button>
            </div> */}
        </form>
    )
}

export default AuthForm