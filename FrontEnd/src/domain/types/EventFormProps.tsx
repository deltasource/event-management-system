import { EventData } from "../models/EventData";
export interface EventFormProps {
  setOpenPopup: React.Dispatch<React.SetStateAction<boolean>>;
  setChildOpenPopup?: React.Dispatch<React.SetStateAction<boolean>>;
  fetchEvents: () => void;
  setCreateEventResponse: (
    message: string,
    severity: "success" | "error"
  ) => void;
  event?: EventData;
  eventId?: string;
}
