export default interface ResponseMessage {
  message: string;
  severity: "error" | "info" | "warning" | "success";
  setResponseMessage: React.Dispatch<React.SetStateAction<string>>;
}
