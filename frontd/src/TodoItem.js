import React, {Component} from "react";
import TextField from "@material-ui/core/TextField";
import "./style.css"

class TodoItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isedit: false
        }
    }

    render() {
        const todo=this.props.todo
        const deletehandler=this.props.deletehandler
        return (
            <li key={todo.id}>
                <TextField className="text"
                           value={todo.taskName}
                           disabled={!this.state.isedit}

                />
                    <button className="edit" type={"submit"}>修改</button>
                    <button className="delete" type={"submit"} onClick={() => deletehandler(todo.id, todo.taskName)}>删除</button>
                
            </li>
        )
    }

}

export default TodoItem