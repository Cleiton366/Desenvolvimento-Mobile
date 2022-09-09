import { TaskRepository } from "../repositories/TaskRepository";

const taskRepository = new TaskRepository();
class TaskController {
  async getTasks(req, res) {
    try {
      const task = await taskRepository.getTasks();
      res.status(200).json(task);
    } catch (error) {
      res.status(500).json(error);
    }
  }

  async submitTask(req, res) {
    const { id, name, estimative, priority, notification } = req.body;
    try {
        const task = await taskRepository.submitTask(id, name, estimative, priority, notification);
        res.status(201).json(task);
      } catch (error) {
        res.status(500).json(error);
      }
  }

  async editTask(req, res) {
    const { id, name, estimative, priority, notification } = req.body;
    try {
        const task = await taskRepository.editTask(id, name, estimative, priority, notification);
        res.status(200).json(task);
      } catch (error) {
        res.status(500).json(error);
      }
  }

  async deleteTask(req, res) {
    const { id } = req.body;
    try {
        const result = await taskRepository.deleteTask(id);
        res.status(200).json({
            message: "Task deleted successfully",
            result: result,
        });
      } catch (error) {
        res.status(500).json(error);
      }
  }
}

export { TaskController };
