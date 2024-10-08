import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
    const [data, setData] = useState('')

    useEffect(() => {
        axios.get('/boards')
            .then(res => {
                console.log("히히히", res.data); // 콘솔에 데이터 출력
                setData(res.data)
            }
            )
            .catch(err => console.log(err))
    }, []);

    return (
        <div>
            받아fdsssdd온 값 : {JSON.stringify(data)} {/* 객체를 문자열로 변환해서 출력 */}
        </div>
    );
}

export default App;