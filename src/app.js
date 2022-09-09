import express from "express";
import { router as taskRouter } from "./routes/Task";

const app = express();
app.use(express.json());
app.use(taskRouter);

export { app };