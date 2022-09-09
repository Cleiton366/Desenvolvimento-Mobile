import "dotenv/config";
import { MongoClient } from "mongodb";

const uri = `mongodb+srv://clei366:${process.env.DB_PASSWORD}@cluster0.sulaxog.mongodb.net/?retryWrites=true&w=majority`;
var db;
var clienteCollection;

MongoClient.connect(uri, { useUnifiedTopology: true }, (error, client) => {
  if (error) {
    console.error("Error while trying to connect to the data base: ", error);
  } else {
    db = client.db("entrega-III-moveis");
    clienteCollection = db.collection("tasks");
  }
});

export { db, clienteCollection };
