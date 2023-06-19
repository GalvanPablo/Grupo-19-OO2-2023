import React from 'react'

import { useSelector, useDispatch } from 'react-redux'
import { setActiveMenu } from './../store/actions/menu.action'

import { Link, NavLink } from 'react-router-dom'

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faWifi } from '@fortawesome/free-solid-svg-icons'

import links from '../data/links'

const Sidebar = () => {
    const dispatch = useDispatch();
    const {activeMenu, screenSize} = useSelector(state => state.menu);

    const handleCloseSideBar = () => {
        if(activeMenu && screenSize <= 900) dispatch(setActiveMenu(false));
    }

    const activeLink = 'flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg text-md text-white m-2';
    const normalLink = 'flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg text-md text-gray-700 dark:text-gray-200 dark:hover:text-black hover:text-black hover:bg-light-gray m-2';

    return (
        <div className='ml-3 h-screen md:overflow-hidden overflow-auto md:hover:overflow-auto pb-10'>
            {activeMenu && (<>
                <div className='flex justify-between items-center'>
                    <Link to="/dashboard" onClick={handleCloseSideBar} className='items-center gap-3 ml-3 mt-4 flex text-xl font-extrabold dark:text-white text-slate-900'>
                        <FontAwesomeIcon icon={faWifi} /><span>Sistemas IoT</span>
                    </Link>
                </div>
                <div className='mt-10'>
                    {links.map((item) => (
                        <div key={item.title}>
                            <p className='text-gray-400 m-3 mt-4 uppercase'>
                                {item.title}
                            </p>
                            {item.links.map((link) => (
                                <NavLink
                                    to={`/${link.name}`}
                                    key={link.name}
                                    onClick={handleCloseSideBar}
                                    className={({ isActive }) => isActive ? activeLink : normalLink}
                                    style={({ isActive }) => isActive ? { background: '#1E40AF' } : {}}
                                >
                                    {link.icon}
                                    <span className='capitalize'>
                                        {link.name}
                                    </span>
                                </NavLink>
                            ))}
                        </div>
                    ))}
                </div>
            </>)}
        </div>
    )
}

export default Sidebar