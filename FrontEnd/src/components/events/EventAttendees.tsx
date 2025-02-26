import { useEffect, useState } from "react";
import { EventAttendeesProps } from "../../domain/models/EventAttendeesProps";
import { fetchAttendeesByEvent } from "../../service/EventService";
import { User } from "../../domain/models/User";
import PersonIcon from "@mui/icons-material/Person";
import {
  Divider,
  List,
  ListItem,
  ListItemText,
  Typography,
} from "@mui/material";

export default function EventAttendees({
  eventId,
  eventName,
  setResponse,
  setOpenPopup,
}: EventAttendeesProps) {
  const [attendees, setAttendees] = useState<User[]>([]);
  const fetchAttendees = async () => {
    try {
      if (eventId) {
        const response = await fetchAttendeesByEvent(eventId);
        setAttendees(response);
      }
    } catch (error: any) {
      setResponse("Failed to load attendees! Please try again later.", "error");
      setOpenPopup(false);
    }
  };

  useEffect(() => {
    fetchAttendees();
  }),
    [eventId];

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        {eventName}
      </Typography>

      {attendees && attendees.length > 0 ? (
        <List>
          {attendees.map((attendee, index) => (
            <div key={attendee.id}>
              <ListItem>
                <PersonIcon sx={{ marginRight: 2 }} />
                <ListItemText
                  primary={`${attendee.firstName} ${attendee.lastName}`}
                  secondary={`Email: ${attendee.email}`}
                />
              </ListItem>
              {index < attendees.length - 1 && (
                <Divider sx={{ borderBottomWidth: 2, borderColor: "black" }} />
              )}
            </div>
          ))}
        </List>
      ) : (
        <Typography variant="body1">No attendees yet.</Typography>
      )}
    </div>
  );
}
