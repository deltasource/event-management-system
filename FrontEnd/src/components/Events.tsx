import { useEffect, useState } from "react";

interface Event {
    id: number;
    name: string;
    dateTime: string;
    venue: string;
    ticketPrice: number;
}

function Events() {
    const[events, setEvents] = useState<Event[]>([]);
    const[error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchEvents = async () => {
          try {
            const response = await fetch("http://localhost:8080/getAll");
            if (!response.ok) {
              throw new Error("Failed to fetch events");
            }
            const eventsData = await response.json();
            setEvents(eventsData);
          } catch (error: any) {
            setError(error.message);
          }
        };
    
        fetchEvents();
      }, []);

    return (
        <div>
          <h1>Events</h1>
          {error && <div className="alert alert-danger">{error}</div>}
          <div className="list-group">
            {events.map((event, index) => (
              <div key={index} className="list-group-item">
                <h5>{event.name}</h5>
                <p>Date: {event.dateTime}</p>
                <p>Place: {event.venue}</p>
                <p>Ticket price: {event.ticketPrice} $</p>
              </div>
            ))}
          </div>
        </div>
      );  }
  
  export default Events;