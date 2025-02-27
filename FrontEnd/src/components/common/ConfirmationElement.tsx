import { Button } from "@mui/material";
import { ConfirmationElementProps } from "../../domain/types/ConfirmationElementPorps";

const warningIconStyle: React.CSSProperties = {
  fontSize: "25px",
  marginRight: "10px",
  color: "#FFA500",
};

export default function ConfirmationElement({
  title,
  icon,
  confirmColor,
  cancelColor,
  onConfirm,
  onCancel,
}: ConfirmationElementProps) {
  return (
    <div>
      <div className="d-flex">
        <span role="img" aria-label="warning" style={warningIconStyle}>
          {icon}
        </span>
        <h3>{title}</h3>
      </div>
      <div>
        <Button
          variant="contained"
          color={cancelColor}
          onClick={onCancel}
          sx={{ marginRight: 2 }}
        >
          Cancel
        </Button>
        <Button variant="contained" color={confirmColor} onClick={onConfirm}>
          Confirm
        </Button>
      </div>
    </div>
  );
}
