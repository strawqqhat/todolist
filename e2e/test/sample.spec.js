describe('Todo List', function () {
    let page;
    before (async function () {
        page = await browser.newPage();
        await page.goto('http://127.0.0.1:3000');
    });

    after (async function () {
        await page.close();
    });
    describe('get all tasks', function() {
        it('should return all tasks correctly', async function () {
            let todoList = await page.$$('#root > div > div > li');
            console.log(todoList.length);
        });
    });

    describe('add a new task', function() {
        it('should add a new task correctly', async function(){
            let todoList = await page.$$('#root > div > div > li');
            const len1 = todoList.length;
            await page.click('.task-input', {delay: 500});
            await page.type('.task-input', 'the ewwwww', {delay: 500});
            await page.click('.submit');
            // 之前没有设置等待时间总是导致新录入信息没加载出来就去获取，得不到新数据
            await page.waitFor(1000);
            let new_todoList = await page.$$('#root > div > div > li');
            const len2 = new_todoList.length;
            expect (len2-len1).to.eql(1);
        });
    });

    describe('delete a task', function() {
        it('should delete a task correctly', async function() {
            let todoList = await page.$$('#root > div > div > li');
            const len1 = todoList.length;
            await page.evaluate(()=> {
                document.querySelector('.delete').click()
            });
            await page.waitFor(1000);
            const len2 = await page.$$eval('#root > div > div > li', list=>list.length);
            expect (len1-len2).to.eql(1);
        });
    });
    
});