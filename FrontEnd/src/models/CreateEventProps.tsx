export interface CreateEventProps {
  setOpenPopup: React.Dispatch<React.SetStateAction<boolean>>;
  fetchEvents: () => void;
  setCreateEventResponse: (
    message: string,
    severity: "success" | "error"
  ) => void;
}
