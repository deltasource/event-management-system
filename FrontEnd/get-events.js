/**
 * This method is responsible for fetching all current events
 * from a .json file. It collects the events information in html
 * and appends it to a html file in the correct position. 
 */
async function getAllEvents() {
    try {
        const response = await fetch('http://localhost:8080/getAll');
        const events = await response.json();
        
        const eventsContainer = document.querySelector(".events-list");
        events.forEach(event => {
            const eventElement = document.createElement('li');
            eventElement.classList.add('event');
            eventElement.innerHTML = `
                <h2>Event name: ${event.name}</h2>
                <p>Date: ${event.dateTime}</p>
                <p>Venue: ${event.venue}</p>
                <p>Ticket price: ${event.ticketPrice}</p>
            `;
            eventsContainer.appendChild(eventElement);
        });
    } catch (error) {
        console.error('Error loading the events data:', error);
    }
}
if (typeof window !== 'undefined') {
    window.getAllEvents = getAllEvents;
}
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { getAllEvents };
}
