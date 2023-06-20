import React from 'react'
import { Link } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faScrewdriverWrench } from '@fortawesome/free-solid-svg-icons';

const DispositivosAlumbrado = () => {
    return (
        <section className="flex items-center h-full p-16 dark:bg-gray-900 dark:text-gray-100">
            <div className="container flex flex-col items-center justify-center px-5 mx-auto my-8">
                <div className="max-w-lg text-center">
                    <h2 className="mb-8 font-extrabold text-9xl dark:text-gray-600">
                        <span className="sr-only">Error</span>
                        <FontAwesomeIcon icon={faScrewdriverWrench} />
                    </h2>
                    <p className="text-2xl font-semibold md:text-3xl">Sitio web en construcción</p>
                    <p className="mt-4 mb-8 dark:text-gray-400">
                        Estamos trabajando en esta sección, por favor vuelva más tarde.
                    </p>
                    <Link to='/' className="px-8 py-3 font-semibold rounded dark:bg-violet-400 dark:text-gray-900">
                        Volver a la página de inicio
                    </Link>
                </div>
            </div>
        </section>
    )
}

export default DispositivosAlumbrado