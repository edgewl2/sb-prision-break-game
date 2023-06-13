package ni.dev.edgeahz.game.service

import ni.dev.edgeahz.game.constants.Constants
import ni.dev.edgeahz.game.data.PrisonBreakGameData as data
import ni.dev.edgeahz.game.domain.entity.Register
import ni.dev.edgeahz.game.repository.RegisterRepository
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Title

@Title("Test suite prison break game service")
class PrisonBreakGameServiceSpec extends Specification {

    RegisterRepository registerRepository;

    PrisonBreakService service;

    def setup() {
        registerRepository = Mock(RegisterRepository);
        service = new PrisonBreakServiceImpl(registerRepository);
    }

    def "Should search if the prisoner can escape"() {
        given: "Prepare data and request object to validate"
            def register = Register.builder()
                    .prison(String.join(Constants.DELIMITER, prison))
                    .escape(canEscape)
                    .build();
            registerRepository.save(_) >> Mono.just(register)
        when: "Call the checker through the algorithm in canEscape"
            def response = service.check(prison)
        then: "We check if the prisoner can escape"
            assert response != null
            assert response.block() == canEscape
        where:
            prison                                      |   canEscape
            data.canEscapeThePrisonHasExit()            |   true
            data.canEscapeThePrisonDoesntHaveExit()     |   false
            data.canEscapeThePrisonDoesntHavePrisoner() |   false
    }

    def "Should show the report of escapes"() {
        given: "Prepare data in process"
            def summary = data.summaryOfSavedEscapes()
            registerRepository.getReportEscape() >> Mono.just(summary)
        when: "Call waiting receive the summary of escapes"
            def response = service.status()
        then: "We check if the prisoner can escape"
            assert response != null
            def values = response.block()
            assert values.getCountSuccessfulEscape() == 7
            assert values.getCountUnsuccessfulEscape() == 12
            assert values.getRatio() == 0.4F
    }
}
