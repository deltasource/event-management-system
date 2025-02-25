export interface ConfirmationElementProps {
  title: string;
  icon: string;
  cancelColor: "error" | "primary" | "secondary";
  confirmColor: "error" | "primary" | "secondary" | "success";
  onConfirm: (data: any) => void;
  onCancel: () => void;
}
