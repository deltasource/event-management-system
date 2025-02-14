import Event from "../models/Event";
import { useState } from "react";
import { CardContent, Card, Button } from "@mui/material";
import * as eventService from "../service/EventService.tsx";
import PopupElement from "../components/UI/Popup.tsx";
import EventForm from "./EventForm.tsx";
import ConfirmationElement from "./ConfirmationElement.tsx";

interface EventDetailsProps {
  event: Event | null;
  setResponse: (message: string, severity: "success" | "error") => void;
  setOpenPopup: React.Dispatch<React.SetStateAction<boolean>>;
  fetchEvents: () => void;
}

export default function EventDetails({
  event,
  setResponse,
  setOpenPopup,
  fetchEvents,
}: EventDetailsProps) {
  const [openUpdatePopup, setOpenUpdatePopup] = useState(false);
  const [deleteConfirmationPopup, setDeleteConfirmationPopup] = useState(false);

  const handleConfirmedDelete = async () => {
    if (event?.id) {
      try {
        const response = await eventService.deleteEvent(event.id);
        setResponse(response, "success");
        fetchEvents();
        setOpenPopup(false);
      } catch (error: any) {
        setResponse(error.message, "error");
      }
    }
  };

  const handleDelete = () => {
    setDeleteConfirmationPopup(true);
  };

  const showPopup = () => {
    setOpenUpdatePopup(true);
  };

  const handleCancelDelete = () => {
    setDeleteConfirmationPopup(false);
  };

  if (!event) return <div>No event selected</div>;
  return (
    <Card variant="outlined" sx={{ maxWidth: 500, margin: "auto" }}>
      <CardContent>
        <div>
          <h2>{event.name}</h2>
          <p>
            <strong>When:</strong> {event.dateTime}
          </p>
          <p>
            <strong>Where:</strong> {event.venue}
          </p>
          <p>
            <strong>Price:</strong> ${event.ticketPrice}
          </p>
          <p>
            <strong>Venue capacity:</strong> {event.maxCapacity}
          </p>
          <p>
            <strong>Organizer Details:</strong> {event.organizerDetails}
          </p>
        </div>
        <div>
          <PopupElement
            title="EDIT EVENT"
            openPopup={openUpdatePopup}
            setOpen={setOpenUpdatePopup}
          >
            <EventForm
              fetchEvents={fetchEvents}
              setOpenPopup={setOpenUpdatePopup}
              setChildOpenPopup={setOpenPopup}
              setCreateEventResponse={setResponse}
              eventId={event.id}
              event={event}
            />
          </PopupElement>
          <Button
            variant="contained"
            color="primary"
            sx={{ marginRight: 2 }}
            onClick={showPopup}
          >
            Edit
          </Button>
          <PopupElement
            title="CONFIRM EVENT DELETION"
            openPopup={deleteConfirmationPopup}
            setOpen={setDeleteConfirmationPopup}
          >
            <ConfirmationElement
              onCancel={handleCancelDelete}
              onConfirm={handleConfirmedDelete}
            />
          </PopupElement>
          <Button variant="contained" color="error" onClick={handleDelete}>
            Delete
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}
