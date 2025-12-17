/*Dropdown Menu*/
$('.dropdown').click(function () {
//    console.log(this);
    $(this).attr('tabindex', 1).focus();
    $(this).toggleClass('active');
    $(this).find('.dropdown-menu').slideToggle(300);
});
$('.dropdown').focusout(function () {
//    console.log(this);
    $(this).removeClass('active');
    $(this).find('.dropdown-menu').slideUp(300);
});
$('.dropdown .dropdown-menu li').click(function () {
//    console.log(this);
    $(this).parents('.dropdown').find('span').text($(this).text());
    $(this).parents('.dropdown').find('input').attr('value', $(this).data('value'));
});
/*Fim Dropdown Menu*/


$('.dropdown-menu li').click(function () {
    var input = '<strong>' + $(this).parents('.dropdown').find('input').val() + '</strong>',
            msg = '<span class="msg">Hidden input value: ';
    $('.msg').html(msg + input + '</span>');
});


/*Funções Selects*/
/*Funções para selecionar o valor correto nos selects dos edits*/
$(document).ready(function () {
    $('.dropdown-edit').each(function () {
        let valor = $(this).find('input').val();
//        console.log($(this).find('input').val());
        $(this).find('.dropdown-menu li').each(function () {
            if ($(this).data('value') == valor) {
                $(this).addClass('selected');
                $(this).parents('.dropdown-edit').find('span').text($(this).text());
                $(this).parents('.dropdown-edit').find('input').attr('value', $(this).data('value'));
            }
        });
    });
});


/* Fim Funções Selects */