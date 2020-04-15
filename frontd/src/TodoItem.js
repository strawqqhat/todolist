import React, {Component} from "react";
import TextField from "@material-ui/core/TextField";

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
                <div className="tools">
                    <button type={"submit"}>修改</button>
                    <button type={"submit"} onClick={() => this.state.deletehandler(todo.id, todo.taskName)}>删除</button>
                </div>
            </li>
        )
    }

}

export default TodoItem