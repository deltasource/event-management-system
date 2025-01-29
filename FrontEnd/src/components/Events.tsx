import { useEffect, useState } from "react";
import Event from "../models/Event.tsx";
import MySnackbar from "./MySnackbar.tsx";

function Events() {
  const [events, setEvents] = useState<Event[]>([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await fetch("http://localhost:8080/getAll");
        const eventsData = await response.json();
        setEvents(eventsData);
      } catch (error: any) {
        setErrorMessage("Failed to load events! Please try again later.");
      }
    };

    fetchEvents();
  }, []);

  return (
    <div>
      <h1>Events</h1>
      {errorMessage && <MySnackbar message={errorMessage}></MySnackbar>}
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
  );
}

export default Events;
