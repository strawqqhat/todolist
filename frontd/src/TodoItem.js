import React, {Component} from "react";
import TextField from "@material-ui/core/TextField";
import "./style.css"

class TodoItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            todo: props.todo,
            deletehandler: props.deletehandler,
            isedit: false
        }
    }

    render() {
        const {todo, isedit} = this.state
        return (
            <li key={todo.id}>
                <TextField className="text"
                           value={todo.taskName}
                           disabled={!isedit}

                />
                    <button className="edit" type={"submit"}>修改</button>
                    <button className="delete" type={"submit"} onClick={() => this.state.deletehandler(todo.id, todo.taskName)}>删除</button>
                
            </li>
        )
    }

}

export default TodoItem