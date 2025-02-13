import { EventData } from "./EventData";
export interface CreateEventProps {
  setOpenPopup: React.Dispatch<React.SetStateAction<boolean>>;
  fetchEvents: () => void;
  setCreateEventResponse: (
    message: string,
    severity: "success" | "error"
  ) => void;
  event?: EventData;
  eventId?: string;
}
