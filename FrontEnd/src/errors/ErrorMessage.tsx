import React, { useState, useEffect } from "react";
import { Snackbar, Alert } from "@mui/material";
import ErrorMessageProps from "./ErrorMessageProps";

/**
 * Component, responsible for presenting an error message within a Snackbar component
 **/
export default function ErrorMessage(errorProps: ErrorMessageProps) {
  const message = errorProps.message;
  const [open, setOpen] = useState(false);

  useEffect(() => {
    if (message) {
      setOpen(true);
    }
  }, [message]);

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <React.Fragment>
      <Snackbar
        open={open}
        autoHideDuration={5000}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center",
        }}
      >
        <Alert severity="error" sx={{ width: "100%" }} onClose={handleClose}>
          {message}
        </Alert>
      </Snackbar>
    </React.Fragment>
  );
}
