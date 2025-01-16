/**
 * This method is responsible for fetching all current events
 * from a .json file. It collects the events information in html
 * and appends it to a html file in the correct position. 
 */
async function getAllEvents() {
    try {
        const response = await fetch('get-events.json');
        const result = await response.json();
        
        const eventsContainer = document.querySelector(".events-list");
        result.events.forEach(event => {
            const eventElement = document.createElement('li');
            eventElement.classList.add('event');
            eventElement.innerHTML = `
                <h2>Event name: ${event.name}</h2>
                <p>Date: ${event.date}</p>
                <p>Venue: ${event.venue}</p>
                <p>Category: ${event.category}</p>
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
