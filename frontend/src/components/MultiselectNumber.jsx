import React, { useState, useEffect } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';

const MultiselectNumber = ({ onChange, placeholder, value = [] }) => {
    const[options, setOptions] = useState(value);
    const [inputValue, setInputValue] = useState('');

    useEffect(() => {
        onChange(options);
    }, [options]);

    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };

    const handleInputKeyDown = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            addOption();
        }
    };

    const addOption = () => {
        const newOption = parseInt(inputValue);
        if (!isNaN(newOption) && !options.includes(newOption)) {
            const newOptions = [...options, newOption];
            newOptions.sort((a, b) => a - b);
            setOptions(newOptions);
            setInputValue('');
        }
    };

    const removeOption = (option) => {
        const newOptions = options.filter((item) => item !== option);
        setOptions(newOptions);
    };

    return (
        <div>
            <div>
                <input
                    type="text"
                    value={inputValue}
                    onChange={handleInputChange}
                    onKeyDown={handleInputKeyDown}
                    className="block w-full p-2 rounded-md border-1"
                    placeholder={placeholder}
                />
            </div>
            <ul className='flex flex-wrap gap-1 my-1'>
                {options.map((option) => (
                    <li key={option}
                        className='bg-slate-200 rounded-lg px-1 gap-1'
                    >
                        {option}
                        <button onClick={() => removeOption(option)}><FontAwesomeIcon icon={faXmark} className='text-xs text-slate-500' /></button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default MultiselectNumber