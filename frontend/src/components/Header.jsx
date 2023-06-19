import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const Header = ({category, title, icon}) => {
    return (
        <div className='mb-10'>
            <p className='text-gray-400'>
                {category}
            </p>
            <div className='flex justify-start items-center gap-2'>
                <FontAwesomeIcon icon={icon} className='text-3xl text-slate-700' />
                <p className='text-3xl font-extrabold tracking-tight text-slate-900'>
                    {title}
                </p>
            </div>
        </div>
    )
}

export default Header