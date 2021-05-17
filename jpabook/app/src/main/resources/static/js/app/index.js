const index = {
    init: function () {
        const _this = this;
        /*$('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function (){
            _this.delete();
        });*/
        document.getElementById("submit-save").onclick = _this.save
        // document.getElementById("submit-update").onclick = _this.update
        //document.getElementById("submit-save").onclick = _this.save
    },
    save: function () {
        const data = {
            name: document.getElementById("name").value,
            price: document.getElementById("price").value,
            stockQuantity: document.getElementById("quantity").value
        };

        /*$.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록됐습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })*/

        const request = new XMLHttpRequest();
        request.open('POST', '/items/new', true);
        request.setRequestHeader('Data-Type', 'json');
        request.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

        request.onload = function () {
            alert('아이템이 등록되었습니다.');
            window.location.href = '/items';
        }
        request.onerror = function () {
            alert(JSON.stringify(data));
        }

        request.send(JSON.stringify(data));
    },
    update: function () {
        const data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        const id = $('#id').val();

        $.ajax({
            type: 'PATCH',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정됐습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        const id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8'
        }).done(function () {
            alert('글이 삭제됐습니다.');
            window.location.href = '/';
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    }
};

index.init()