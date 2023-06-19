import React from 'react'
import { AuthForm } from '../components'

const Auth = () => {
    return (
        <div className='flex flex-col flex-auto w-full h-screen'>
            <div className='h-full'>
                <div className='grid lg:grid-cols-3 h-full'>
                    <div className='bg-blue-900 lg:flex hidden'></div>
                    <div className='col-span-2 flex justify-center items-center'>

                        <div className='min-w-[450] px-8'>
                            <div className='mb-8'>
                                <h1 className='text-3xl font-medium'>Bienvenido de nuevo</h1>
                                <p>¡Por favor ingrese sus credenciales para iniciar sesión!</p>
                            </div>

                            <AuthForm />
                        </div>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default Auth