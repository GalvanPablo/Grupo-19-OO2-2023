import React from 'react'

const Modal = ({ isOpen, children, width }) => {
    if (!isOpen) return null

    return (
        <div className="fixed inset-0 flex items-center justify-center z-20">
            <div className="fixed inset-0 bg-black opacity-50"></div>
            <div className={`bg-white p-8 z-30 min-w-[375px] max-w-[500px] w-[${width}]`}>
                {children}
            </div>
        </div>
    )
}

export default Modal