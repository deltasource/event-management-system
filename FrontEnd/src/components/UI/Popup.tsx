import {
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton,
  Box,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

interface PopupProps {
  title: string;
  children: React.ReactNode;
  openPopup: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function PopupElement(props: PopupProps) {
  const { title, children, openPopup, setOpen } = props;

  return (
    <Dialog open={openPopup}>
      <DialogTitle
        className="bg-secondary"
        color="white"
        style={{ display: "flex", justifyContent: "space-between" }}
      >
        {title}
        <Box>
          <IconButton
            onClick={() => setOpen(false)}
            style={{
              backgroundColor: "white",
              color: "red",
            }}
          >
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <DialogContent dividers>{children}</DialogContent>
    </Dialog>
  );
}
