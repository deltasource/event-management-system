import { useEffect, useState } from "react";
import Event from "../../domain/models/Event.tsx";
import ResponseMessage from "../common/ResponseMessage.tsx";
import * as eventService from "../../service/EventService.tsx";
import { Button } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import PopupElement from "../common/Popup.tsx";
import EventDetails from "./EventDetails.tsx";
import EventForm from "./EventForm.tsx";

function Events() {
  const [events, setEvents] = useState<Event[]>([]);
  const [openCreateEventPopup, setOpenCreateEventPopup] = useState(false);
  const [openEventDetailsPopup, setEventDetailsPopup] = useState(false);
  const [responseMessage, setResponseMessage] = useState<string>("");
  const [severity, setSeverity] = useState<"success" | "error">("success");
  const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);

  const handleResponse = (message: string, severity: "success" | "error") => {
    setResponseMessage(message);
    setSeverity(severity);
  };
  const fetchEvents = async () => {
    try {
      const data = await eventService.fetchEvents();
      setEvents(data);
    } catch (error: any) {
      setResponseMessage("Failed to load events! Please try again later.");
      setSeverity("error");
    }
  };

  useEffect(() => {
    fetchEvents();
  }, [responseMessage]);

  return (
    <div>
      <h1>Events</h1>
      <Button
        variant="outlined"
        className="mb-3"
        onClick={() => {
          setOpenCreateEventPopup(true);
        }}
      >
        <AddIcon fontSize="small" />
        Create New Event
      </Button>
      <PopupElement
        title="CREATE NEW EVENT"
        openPopup={openCreateEventPopup}
        setOpen={setOpenCreateEventPopup}
      >
        <EventForm
          fetchEvents={fetchEvents}
          setOpenPopup={setOpenCreateEventPopup}
          setCreateEventResponse={handleResponse}
        />
      </PopupElement>
      <PopupElement
        title="EVENT DETAILS"
        openPopup={openEventDetailsPopup}
        setOpen={setEventDetailsPopup}
      >
        <EventDetails
          event={selectedEvent}
          setOpenPopup={setEventDetailsPopup}
          setResponse={handleResponse}
          fetchEvents={fetchEvents}
        ></EventDetails>
      </PopupElement>
      {responseMessage && (
        <ResponseMessage
          setResponseMessage={setResponseMessage}
          message={responseMessage}
          severity={severity}
        />
      )}
      {events && (
        <div className="list-group">
          {events.map((event, index) => (
            <div
              key={index}
              className="list-group-item"
              onClick={() => {
                setSelectedEvent(event);
                setEventDetailsPopup(true);
              }}
            >
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
