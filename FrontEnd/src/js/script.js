function getAllEvents() {
    let list;
    fetch('../../data/data.json')
    .then(response => response.json()) 
        .then(result => {
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
        })
        .catch(error => console.error('Error loading the events data:', error));
}

getAllEvents();
