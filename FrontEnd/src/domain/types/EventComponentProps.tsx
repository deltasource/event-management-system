import Event from "../models/Event.tsx";

export interface EventComponentProps {
  event: Event | null;
  setResponse: (message: string, severity: "success" | "error") => void;
  setOpenPopup: React.Dispatch<React.SetStateAction<boolean>>;
  fetchEvents: () => void;
}
