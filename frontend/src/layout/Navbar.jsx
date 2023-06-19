import React, { useEffect } from 'react'

import { useSelector, useDispatch } from 'react-redux'
import { setActiveMenu, setScreenSize, setClickedUserProfile } from './../store/actions/menu.action'

import { logout } from './../store/actions/auth.action'

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEllipsis, faChevronDown, faUser, faUserSecret } from '@fortawesome/free-solid-svg-icons'


const NavButton = ({ title, customFunc, icon, color, dotColor }) => (
    <button type='button' onClick={customFunc} style={{ color }}
        className='relative text-xl  rounded-full p-3 hover:bg-light-gray'
    >
        <span style={{ background: dotColor }}
            className='absolute inline-flex rounded-full h-2 w-2 right-2 top-2'
        />
        {icon}
    </button>
)

const Navbar = () => {
    const dispatch = useDispatch();
    const { activeMenu, screenSize, isClickedUserProfile } = useSelector(state => state.menu);
    const { email, role } = useSelector(state => state.auth);

    const handleSetActiveMenu = (active) => dispatch(setActiveMenu(active));

    // Responsive de el sidebar
    useEffect(() => {
        const handleResize = () => dispatch(setScreenSize(window.innerWidth));
        window.addEventListener('resize', handleResize);
        handleResize();
        return () => window.removeEventListener('resize', handleResize);
    }, []);
    useEffect(() => {
        handleSetActiveMenu(screenSize <= 900 ? false : true);
    }, [screenSize]);

    return (
        <div className='flex justify-between p-2 md:mx-6 relative'>
            <NavButton
                title="Menu"
                customFunc={() => handleSetActiveMenu(!activeMenu)}
                color='#1E40AF'
                icon={<FontAwesomeIcon icon={faEllipsis} />}
            />

            <div className='flex'>
                <div className='flex items-center gap-2 cursor-pointer p-1 hover:bg-light-gray rounded-lg'
                    onClick={() => dispatch(setClickedUserProfile(!isClickedUserProfile))}
                >
                    <div className='rounded-full w-8 h-8'>
                        <FontAwesomeIcon icon={role === 'ADMIN' ? faUserSecret : faUser} className='text-gray-600 text-3xl' />
                    </div>
                    <p className='flex flex-row gap-1'>
                        <span className='text-gray-400 text-14'>Hola, </span>
                        <span className='text-gray-400 font-bold ml-1 text-14'>{email}</span>
                    </p>
                    <FontAwesomeIcon icon={faChevronDown} className='text-gray-400 text-14'/>
                </div>

                {isClickedUserProfile && (
                    <div className='absolute right-0 top-12 bg-white rounded-lg shadow-lg p-2'>
                        <button className='text-gray-400 text-14'
                            onClick={() => dispatch(logout())}
                        >
                            Logout
                        </button>
                    </div>
                )}
            </div>
        </div>
    )
}

export default Navbar