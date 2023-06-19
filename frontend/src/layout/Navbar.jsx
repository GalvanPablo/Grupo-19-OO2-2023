import React, { useEffect } from 'react'

import { useSelector, useDispatch } from 'react-redux'
import { setActiveMenu, setScreenSize } from './../store/actions/menu.action'

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEllipsis } from '@fortawesome/free-solid-svg-icons'


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
    const {activeMenu, screenSize} = useSelector(state => state.menu);
    
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
        </div>
    )
}

export default Navbar