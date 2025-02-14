import Event from "../../domain/models/Event.tsx";

export interface EventDetailsProps {
  event: Event | null;
  setResponse: (message: string, severity: "success" | "error") => void;
  setOpenPopup: React.Dispatch<React.SetStateAction<boolean>>;
  fetchEvents: () => void;
}
