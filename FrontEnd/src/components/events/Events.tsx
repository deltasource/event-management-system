import { useEffect, useState } from "react";
import Event from "../../domain/models/Event.tsx";
import ResponseMessage from "../common/ResponseMessage.tsx";
import * as eventService from "../../service/EventService.tsx";
import { Button } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import PopupElement from "../common/Popup.tsx";
import EventDetails from "./EventDetails.tsx";
import EventForm from "./EventForm.tsx";
import BuyTicketForm from "./BuyTicketForm.tsx";
import EventAttendees from "./EventAttendees.tsx";

function Events() {
  const [events, setEvents] = useState<Event[]>([]);
  const [openCreateEventPopup, setOpenCreateEventPopup] = useState(false);
  const [openEventDetailsPopup, setEventDetailsPopup] = useState(false);
  const [openBuyTicketPopup, setBuyTicketPopup] = useState(false);
  const [attendeesPopup, setAttendeesPopup] = useState(false);
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
      <PopupElement
        title="ATTEND THIS EVENT"
        openPopup={openBuyTicketPopup}
        setOpen={setBuyTicketPopup}
      >
        <BuyTicketForm
          event={selectedEvent}
          setOpenPopup={setBuyTicketPopup}
          setResponse={handleResponse}
          fetchEvents={fetchEvents}
        ></BuyTicketForm>
      </PopupElement>
      <PopupElement
        title="ATTENDEES:"
        openPopup={attendeesPopup}
        setOpen={setAttendeesPopup}
      >
        <EventAttendees
          eventId={selectedEvent?.id}
          eventName={selectedEvent?.name}
          setResponse={handleResponse}
          setOpenPopup={setAttendeesPopup}
        ></EventAttendees>
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
              className="list-group-item d-flex justify-content-between align-items-center p-4"
              onClick={() => {
                setSelectedEvent(event);
                setEventDetailsPopup(true);
              }}
            >
              <div>
                <h5>{event.name}</h5>
                <p>Date: {event.dateTime}</p>
                <p>Place: {event.venue}</p>
                <p>Ticket price: {event.ticketPrice} $</p>
              </div>

              <div>
                <button
                  className="btn btn-warning m-5"
                  onClick={(e) => {
                    e.stopPropagation();
                    setSelectedEvent(event);
                    setBuyTicketPopup(true);
                  }}
                >
                  Buy Ticket
                </button>
                <button
                  className="btn btn-info"
                  onClick={(e) => {
                    e.stopPropagation();
                    setSelectedEvent(event);
                    setAttendeesPopup(true);
                  }}
                >
                  View Attendees
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Events;
