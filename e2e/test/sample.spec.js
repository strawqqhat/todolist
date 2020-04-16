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

    describe('add task', function() {
        it('should add a new task correctly', async function(){
            let todoList = await page.$$('#root > div > div > li');
            const len1 = todoList.length;
            await page.click('input[type=text]', {delay: 500});
            await page.type('input[type=text]', 'the ewwwww', {delay: 1000});
            await page.click('button[data-test-id=add-button]');
            // 之前没有设置等待时间总是导致新录入信息没加载出来就去获取，得不到新数据
            await page.waitFor(1000);
            let new_todoList = await page.$$('#root > div > div > li');
            const len2 = new_todoList.length;
            expect (len2-len1).to.eql(1);
        });
    });
    
});