export interface EventAttendeesProps {
  eventId?: string;
  eventName?: string;
  setResponse: (message: string, severity: "success" | "error") => void;
  setOpenPopup: React.Dispatch<React.SetStateAction<boolean>>;
}
