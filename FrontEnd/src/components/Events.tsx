import { useEffect, useState } from "react";
import Event from "../models/Event.tsx";
import ResponseMessage from "./ResponseMessage.tsx";
import * as eventService from "../service/EventService.tsx";
import { Button } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import PopupElement from "./UI/Popup.tsx";
import CreateEvent from "./CreateEvent.tsx";

function Events() {
  const [events, setEvents] = useState<Event[]>([]);
  const [openPopup, setOpenPopup] = useState(false);
  const [responseMessage, setResponseMessage] = useState<string>("");
  const [severity, setSeverity] = useState<"success" | "error">("success");
  const handleCreateEventResponse = (
    message: string,
    severity: "success" | "error"
  ) => {
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
  }, []);

  return (
    <div>
      <h1>Events</h1>
      <Button
        variant="outlined"
        className="mb-3"
        onClick={() => {
          setOpenPopup(true);
        }}
      >
        <AddIcon fontSize="small" />
        Create New Event
      </Button>
      <PopupElement
        title="CREATE NEW EVENT"
        openPopup={openPopup}
        setOpen={setOpenPopup}
      >
        <CreateEvent
          fetchEvents={fetchEvents}
          setOpenPopup={setOpenPopup}
          setCreateEventResponse={handleCreateEventResponse}
        />
      </PopupElement>
      {responseMessage && (
        <ResponseMessage message={responseMessage} severity={severity} />
      )}
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
