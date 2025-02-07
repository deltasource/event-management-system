export default interface ErrorMessage {
  message: string;
  severity: "error" | "info" | "warning" | "success";
}
