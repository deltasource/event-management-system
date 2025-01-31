import { useEffect, useState } from "react";
import Event from "../models/Event.tsx";
import ErrorMessage from "../errors/ErrorMessage.tsx";
import * as eventService from "../service/EventService.tsx";

function Events() {
  const [events, setEvents] = useState<Event[]>([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await eventService.fetchEvents();
        setEvents(data);
      } catch (error: any) {
        setErrorMessage("Failed to load events! Please try again later.");
      }
    };
    fetchData();
  }, []);

  return (
    <div>
      <h1>Events</h1>
      {errorMessage && <ErrorMessage message={errorMessage}></ErrorMessage>}
      {events && (
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
      )}
    </div>
  );
}

export default Events;
