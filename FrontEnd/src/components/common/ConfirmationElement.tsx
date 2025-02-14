import { Button } from "@mui/material";
interface ConfirmationElementProps {
  onConfirm: () => void;
  onCancel: () => void;
}

const warningIconStyle: React.CSSProperties = {
  fontSize: "25px",
  marginRight: "10px",
  color: "#FFA500",
};

export default function ConfirmationElement({
  onConfirm,
  onCancel,
}: ConfirmationElementProps) {
  return (
    <div>
      <div className="d-flex">
        <span role="img" aria-label="warning" style={warningIconStyle}>
          ⚠️
        </span>
        <h3>Are you sure you want to delete this event?</h3>
      </div>
      <div>
        <Button
          variant="contained"
          color="primary"
          onClick={onCancel}
          sx={{ marginRight: 2 }}
        >
          Cancel
        </Button>
        <Button variant="contained" color="error" onClick={onConfirm}>
          Confirm
        </Button>
      </div>
    </div>
  );
}
