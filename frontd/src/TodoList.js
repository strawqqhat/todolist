import React ,{Component}from "react";
import axios from "axios";
class TodoList extends Component {
    constructor(props) {
        super(props);
        this.state={
           inputThing:'',
            error:'',
            hasError: false,
            todos: [
                {id:'df',sdf:'asdf'}
            ]
        }
    }
    //从后端获取数据
    componentDidMount() {
        axios.get('/api/list').then(res=>{
            const todos=res.data;
             console.log(JSON.stringify(todos["data"]))
            this.setState({
                todos:todos["data"]
            })

    })
    }
    render() {
        const {inputthing,todos,error,hasError}=this.state;
        return(
            <div>
                {
                    todos.map(todo=><p key={todo.id}>{todo.taskName}</p>)
                }

            </div>
        )

    }
}export default TodoList;