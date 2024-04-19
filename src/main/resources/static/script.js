document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.getElementById('fileInput');
    const tagInput = document.getElementById('tagInput');
    const categoryInput = document.getElementById('categoryInput');
    const saveButton = document.getElementById('saveButton');
    const resultArea = document.getElementById('resultArea');
    const valueInput = document.getElementById('valueInput');
    const resultTableBody = document.getElementById('resultTableBody');
    const showDBButton = document.getElementById('showDBButton');

    //저장 버튼
    saveButton.addEventListener('click', function() {
        const tag = tagInput.value.trim();
        const category = categoryInput.value.trim();

        //미입력시 경고
        if (!tag) {
            alert('태그를 입력해주세요.');
            return;
        }
        if (!category) {
            alert('Category 값을 입력해주세요.');
            return;
        }

        const file = fileInput.files[0];
        const reader = new FileReader();
        reader.onload = function(event) {

            const xmlString = event.target.result;
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(xmlString, 'text/xml');
            const value = findValue(xmlDoc, tag, category);

            //결과값이 있으면 저장
            if (value !== null) {
                valueInput.value = value;
                sendDataToServer(tag, category, value);
            } else {
            //결과값이 없으면 경고
                alert('결과값이 없습니다.');
            }
        };
        reader.readAsText(file);
    });

    function findValue(xmlDoc, tag, category) {
        const elements = xmlDoc.getElementsByTagName(tag);
            for (let i = 0; i < elements.length; i++) {
                const element = elements[i];
                if (element.getAttribute('category') === category) {
                    return element.getAttribute('value');
                }
            }
        return null;
    }

    //데이터 전송
    function sendDataToServer(tag, category, value) {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/save-to-db', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert(xhr.responseText);
                } else {
                    alert('오류가 발생했습니다.');
                }
            }
        };
        const data = JSON.stringify({ tag: tag, category: category, value: value });
        xhr.send(data);
    }

    //전체 DB 조회 버튼
    showDBButton.addEventListener('click', function() {
        fetchDBList();
    });

    //서버에서 DB 목록 가져오기
    function fetchDBList() {
        fetch('/load-to-db')
            .then(response => response.json())
            .then(data => {
                displayDBList(data);
            })
            .catch(error => console.error('Error:', error));
    }

    //테이블에 DB 목록 출력
    function displayDBList(data) {
        resultTableBody.innerHTML = '';

        data.forEach(row => {
            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                <td>${row.id}</td>
                <td>${row.tag}</td>
                <td>${row.category}</td>
                <td>${row.value}</td>
            `;
            resultTableBody.appendChild(newRow);
        });
    }
});
