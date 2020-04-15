import React ,{Component}from "react";
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
            <div>
                <span className="text" key={key}>
        {todo.taskName}
      </span>
                <div className="tools">
                    <button type={"submit"}>修改</button>
                    <button type={"submit"} onClick={()=>this.state.deletehandler(key,todo.taskName)}>删除</button>
                </div>
            </div>
        )
    }

}export default TodoItem