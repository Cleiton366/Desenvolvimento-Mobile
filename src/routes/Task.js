import { Router } from "express";
import { TaskController } from "../controllers/TaskController";

const router = Router();
const taskController = new TaskController();

router.get("/tasks-list", taskController.getTasks);
router.post("/submit-task", taskController.submitTask);
router.put("/update-task", taskController.editTask);
router.delete("/delete-task", taskController.deleteTask);

export { router };
