import React ,{Component}from "react";
import TextField from "@material-ui/core/TextField";
class TodoItem extends Component{
    constructor(props) {
        super(props);
        this.state={
            todo:props.todo,
            key:props.key,
            deletehandler:props.deletehandker
        }
    }
    render() {
        const {todo,key}=this.state
        return(
            <li key={key}>
                <TextField className="text"
                value={todo.taskName}
                ></TextField>
                <div className="tools">
                    <button type={"submit"}>修改</button>
                    <button type={"submit"} onClick={()=>this.state.deletehandler(key,todo.taskName)}>删除</button>
                </div>
            </li>
        )
    }

}export default TodoItem