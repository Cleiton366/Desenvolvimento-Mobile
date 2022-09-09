import { db, clienteCollection } from "../db/mongodb";

class TaskRepository {
  async getTasks() {
    try {
      return await db.collection("tasks").find().toArray();
    } catch (err) {
      console.log("Error while trying to get tasks list", err);
      return err;
    }
  }

  async submitTask(id, name, estimative, priority, notification) {
    const task = {
      id: id,
      name: name,
      estimative: estimative,
      priority: priority,
      notification: notification,
    };
    try {
      await clienteCollection.insertOne(task);
      return {
        message: "Task created successfully",
        task: task,
      };
    } catch (err) {
      console.error("Error while trying to create task: ", err);
      return err;
    }
  }

  async editTask(id, name, estimative, priority, notification) {
    const task = {
      id: id,
      name: name,
      estimative: estimative,
      priority: priority,
      notification: notification,
    };

    try {
      const result = await clienteCollection.findOneAndUpdate(
        { id: task.id },
        {
          $set: {
            name: task.name,
            estimative: task.estimative,
            priority: task.priority,
            notification: task.notification,
          },
        }
      );

      return {
        message: "Task updated successfully",
        result: result,
        task: task,
      };
    } catch (err) {
      console.error("Error while trying to edit task info: ", err);
      return err;
    }
  }
  async deleteTask(id) {
    await clienteCollection
      .deleteOne({ id: id })
      .catch((err) => {
        console.error("Error while trying to edit task info: ", err);
        return err;
      });
  }
}

export { TaskRepository };
