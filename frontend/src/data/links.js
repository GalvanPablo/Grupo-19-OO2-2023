import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChartSimple, faCar, faDroplet, faLightbulb } from '@fortawesome/free-solid-svg-icons'

const links = [
    {
        title: 'principal',
        links: [
            {
                name: 'dashboard',
                icon: <FontAwesomeIcon icon={faChartSimple} />,
            },
        ],
    },
    {
        title: 'soluciones',
        links: [
            {
                name: 'estacionamiento',
                icon: <FontAwesomeIcon icon={faCar} />
            },
            {
                name: 'riego',
                icon: <FontAwesomeIcon icon={faDroplet} />
            },
            {
                name: 'alumbrado',
                icon: <FontAwesomeIcon icon={faLightbulb} />,
            },
        ],
    },
];

export default links;