package net.thumbtack.school.elections;

import com.google.gson.Gson;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exceptions.ElectionErrors;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.general.*;
import net.thumbtack.school.elections.request.*;
import net.thumbtack.school.elections.response.*;
import net.thumbtack.school.elections.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestElections {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Gson gson = new Gson();
    private RegisterVoterDtoRequest request1, request2, request3, request4, request5, request6, request7,
            request8, request9, request10, request11;
    private Server server;

    @Before
    public void init() {
        request1 = new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy");
        request2 = new RegisterVoterDtoRequest("Элис", "Купер",
                "Ист-Джефферсон авеню", "64", 12, "PrinceOfDarknessToo", "Trash");
        request3 = new RegisterVoterDtoRequest("Блэки", "Лоулесс",
                "Статен-Айленд", "55", 10, "WildChild", "W.A.S.P");
        request4 = new RegisterVoterDtoRequest("Валерий", "Кипелов", "Александрович",
                "Мусы Джалиля", "27", 32, "DeadZone", "Kipelich");
        request5 = new RegisterVoterDtoRequest("Ронни", "Дио", "Джеймс",
                "Джерси-роад", "12", "HollyDiver", "WeRock");
        request6 = new RegisterVoterDtoRequest("Удо", "Диркшнайдер",
                "Зюдштрассе", "45", 1147, "Breaker", "SonOfA@#$%^");
        request7 = new RegisterVoterDtoRequest("Ники", "Сикс",
                "Коронет-драйв", "1", "MotleyCrue", "Sixx1234");
        request8 = new RegisterVoterDtoRequest("Джин", "Симмонс",
                "Ханаркизим-стрит", "7", 8, "GodOfThunder", "MoneyMoneyMoney");
        request9 = new RegisterVoterDtoRequest("Брюс", "Дикинсон",
                "Элберт-стрит", "7d", 10, "Trooper", "IronMaidenForever");
        request10 = new RegisterVoterDtoRequest("Роб", "Хелфорд",
                "Марш-стрит", "65", "TurboLover", "ElectricEye");
        request11 = new RegisterVoterDtoRequest("Джон", "Джови", "Бон",
                "Лоретта-стрит", "10А", 1, "BonJovi", "ItsMyLife");
        server = new Server();
        try {
            server.startServer(null);
        } catch (ClassNotFoundException | IOException e) {
            fail();
        }
        assertTrue(server.isStartingIsDone());
    }

    @After
    public void end() {
        try {
            server.stopServer(null);
        } catch (IOException e) {
            fail();
        }
        assertFalse(server.isStartingIsDone());
    }

    @Test
    public void testServer() {
        testTrueElections();
        File fileName = null;
        try {
            fileName = folder.newFile("True_Elections.txt");
            server.stopServer(fileName.getPath());
        } catch (IOException e) {
            fail();
        }
        DataBase databaseToWrite = server.getDatabase();
        assertNotNull(databaseToWrite);
        assertTrue(fileName.exists());
        try {
            server.startServer(fileName.getPath());
        } catch (ClassNotFoundException | IOException e) {
            fail();
        }
        assertEquals(databaseToWrite, server.getDatabase());
    }

    @Test
    public void testRegisterVoter1() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonResponce1 = server.registerVoter(jsonRequest1);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponce1, RegisterVoterDtoResponse.class);
        String jsonRequest4 = gson.toJson(request4);
        String jsonResponce4 = server.registerVoter(jsonRequest4);
        RegisterVoterDtoResponse response4 = gson.fromJson(jsonResponce4, RegisterVoterDtoResponse.class);
        String jsonRequest5 = gson.toJson(request5);
        String jsonResponce5 = server.registerVoter(jsonRequest5);
        RegisterVoterDtoResponse response5 = gson.fromJson(jsonResponce5, RegisterVoterDtoResponse.class);
        String jsonRequest9 = gson.toJson(request9);
        String jsonResponce9 = server.registerVoter(jsonRequest9);
        RegisterVoterDtoResponse response9 = gson.fromJson(jsonResponce9, RegisterVoterDtoResponse.class);

        VoterListDtoRequest voterListDtoRequest = new VoterListDtoRequest(response1.getToken());
        String jsonVoterListRequest = gson.toJson(voterListDtoRequest);
        String jsonVoterListResponce = server.getVoterList(jsonVoterListRequest);
        VoterListDtoResponse voterListDtoResponse = gson.fromJson(jsonVoterListResponce, VoterListDtoResponse.class);
        List<Voter> list1 = voterListDtoResponse.getVoterList();
        assertEquals(4, list1.size());
    }

    @Test
    public void testRegisterVoterValidation() {
        List<RegisterVoterDtoRequest> requestList = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList = new ArrayList<>();
        requestList.add(new RegisterVoterDtoRequest(null, "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("оЗзи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Ozzy", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", null,
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "оЗборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Ozborn",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                null, "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                " Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Dixon Trail Road", "5535", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", null, "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "@", "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", -1, "PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", null, "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", " PrinceOfDarkness", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "Prin", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness12345", "NiceGuy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", null));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", ""));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "Nice Guy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "niceguy"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "Nice"));
        requestList.add(new RegisterVoterDtoRequest("Оззи", "Озборн",
                "Диксон-трейл-роад", "5535", "PrinceOfDarkness", "NiceGuyOzzyOsbornePrinceOfDarkness"));
        requestList.add(new RegisterVoterDtoRequest("Валерий", "Кипелов", "",
                "Мусы Джалиля", "27", 32, "DeadZone", "Kipelich"));
        requestList.add(new RegisterVoterDtoRequest("Валерий", "Кипелов", "александрович",
                "Мусы Джалиля", "27", 32, "DeadZone", "Kipelich"));
        requestList.add(new RegisterVoterDtoRequest("Валерий", "Кипелов", "аЛександрович",
                "Мусы Джалиля", "27", 32, "DeadZone", "Kipelich"));
        requestList.add(new RegisterVoterDtoRequest("Валерий", "Кипелов", "Aleksandrovich",
                "Мусы Джалиля", "27", 32, "DeadZone", "Kipelich"));
        for (RegisterVoterDtoRequest request : requestList) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.registerVoter(jsonRequest);
            responseList.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 4; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_FIRSTNAME.getErrorString(), responseList.get(i).getError());
        }
        for (int i = 5; i <= 9; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_LASTNAME.getErrorString(), responseList.get(i).getError());
        }
        for (int i = 10; i <= 13; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_STREET.getErrorString(), responseList.get(i).getError());
        }
        for (int i = 14; i <= 16; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_HOUSE.getErrorString(), responseList.get(i).getError());
        }
        assertEquals(ElectionJsonParsingErrorCode.WRONG_APARTMENTS.getErrorString(), responseList.get(17).getError());
        for (int i = 18; i <= 22; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_LOGIN.getErrorString(), responseList.get(i).getError());
        }
        for (int i = 23; i <= 28; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_PASSWORD.getErrorString(), responseList.get(i).getError());
        }
        for (int i = 29; i <= 32; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_PATRONYMIC.getErrorString(), responseList.get(i).getError());
        }
    }

    @Test
    @SuppressWarnings("unused")
    public void testRegisterVoterWithError() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);
        request2 = new RegisterVoterDtoRequest("Элис", "Купер",
                "Ист-Джефферсон авеню", "64", 12, "PrinceOfDarkness", "Trash");
        String jsonRequest2 = gson.toJson(request2);
        String jsonResponce2 = server.registerVoter(jsonRequest2);
        ElectionErrorDtoResponse response2 = gson.fromJson(jsonResponce2, ElectionErrorDtoResponse.class);
        assertEquals(ElectionErrorCode.DUPLICATE_LOGIN.getErrorString(), response2.getError());
    }

    @Test
    public void testGetVoterListAndAddAgreeRemoveCandidate() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonRequest2 = gson.toJson(request2);
        String jsonRequest3 = gson.toJson(request3);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        String jsonResponse2 = server.registerVoter(jsonRequest2);
        String jsonResponse3 = server.registerVoter(jsonRequest3);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response2 = gson.fromJson(jsonResponse2, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response3 = gson.fromJson(jsonResponse3, RegisterVoterDtoResponse.class);

        VoterListDtoRequest voterListDtoRequest = new VoterListDtoRequest(response1.getToken());
        String jsonVoterListRequest = gson.toJson(voterListDtoRequest);
        String jsonVoterListResponce = server.getVoterList(jsonVoterListRequest);
        VoterListDtoResponse voterListDtoResponse = gson.fromJson(jsonVoterListResponce, VoterListDtoResponse.class);
        List<Voter> list1 = voterListDtoResponse.getVoterList();
        assertEquals(3, list1.size());

        sortVoterList(list1);
        Voter voter1 = list1.get(1);

        AddCandidateDtoRequest addCandidateDtoRequest1 = new AddCandidateDtoRequest(response1.getToken(), voter1.getId());
        String jsonAddCandidateRequest1 = gson.toJson(addCandidateDtoRequest1);
        String jsonAddCandidateResponce1 = server.addCandidate(jsonAddCandidateRequest1);
        AddCandidateDtoResponse addCandidateDtoResponse1 = gson.fromJson(jsonAddCandidateResponce1, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse1.getStatus());

        Voter voter2 = list1.get(2);

        AddCandidateDtoRequest addCandidateDtoRequest2 = new AddCandidateDtoRequest(response2.getToken(), voter2.getId());
        String jsonAddCandidateRequest2 = gson.toJson(addCandidateDtoRequest2);
        String jsonAddCandidateResponce2 = server.addCandidate(jsonAddCandidateRequest2);
        AddCandidateDtoResponse addCandidateDtoResponse2 = gson.fromJson(jsonAddCandidateResponce2, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse2.getStatus());
        assertEquals("Вашу кандидату выставили на пост мэра. Необходимо дать своё согласие.", addCandidateDtoResponse2.getNotice());

        Voter voter3 = list1.get(0);

        AddCandidateDtoRequest addCandidateDtoRequest3 = new AddCandidateDtoRequest(response3.getToken(), voter3.getId());
        String jsonAddCandidateRequest3 = gson.toJson(addCandidateDtoRequest3);
        String jsonAddCandidateResponce3 = server.addCandidate(jsonAddCandidateRequest3);
        AddCandidateDtoResponse addCandidateDtoResponse3 = gson.fromJson(jsonAddCandidateResponce3, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse3.getStatus());

        CandidateAgreeDtoRequest candidateAgreeDtoRequest = new CandidateAgreeDtoRequest(response2.getToken(), true);
        String jsonCandidateAgreeDtoRequest = gson.toJson(candidateAgreeDtoRequest);
        String jsonCandidateAgreeDtoResponce = server.agree(jsonCandidateAgreeDtoRequest);
        CandidateAgreeDtoResponse candidateAgreeDtoResponse = gson.fromJson(jsonCandidateAgreeDtoResponce, CandidateAgreeDtoResponse.class);
        assertEquals("ok", candidateAgreeDtoResponse.getStatus());

        CandidateListDtoRequest candidateListDtoRequest11 = new CandidateListDtoRequest(response1.getToken());
        String jsonCandidateListDtoRequest11 = gson.toJson(candidateListDtoRequest11);
        String jsonCandidateListDtoResponse11 = server.getCandidateList(jsonCandidateListDtoRequest11);
        CandidateListDtoResponse candidateListDtoResponse11 = gson.fromJson(jsonCandidateListDtoResponse11, CandidateListDtoResponse.class);
        assertEquals(1, candidateListDtoResponse11.getCandidateList().size());

        CandidateAgreeDtoRequest candidateDisagreeDtoRequest = new CandidateAgreeDtoRequest(response3.getToken(), false);
        String jsonCandidateDisagreeDtoRequest = gson.toJson(candidateDisagreeDtoRequest);
        String jsonCandidateDisagreeDtoResponce = server.agree(jsonCandidateDisagreeDtoRequest);
        CandidateAgreeDtoResponse candidateDisagreeDtoResponse = gson.fromJson(jsonCandidateDisagreeDtoResponce, CandidateAgreeDtoResponse.class);
        assertEquals("ok", candidateDisagreeDtoResponse.getStatus());

        CandidateListDtoRequest candidateListDtoRequest12 = new CandidateListDtoRequest(response1.getToken());
        String jsonCandidateListDtoRequest12 = gson.toJson(candidateListDtoRequest12);
        String jsonCandidateListDtoResponse12 = server.getCandidateList(jsonCandidateListDtoRequest12);
        CandidateListDtoResponse candidateListDtoResponse12 = gson.fromJson(jsonCandidateListDtoResponse12, CandidateListDtoResponse.class);
        assertEquals(1, candidateListDtoResponse12.getCandidateList().size());

        RemoveCandidateDtoRequest removeCandidateDtoRequest = new RemoveCandidateDtoRequest(response2.getToken());
        String jsonRemoveCandidateDtoRequest = gson.toJson(removeCandidateDtoRequest);
        String jsonRemoveCandidateDtoResponce = server.removeCandidate(jsonRemoveCandidateDtoRequest);
        RemoveCandidateDtoResponse removeCandidateDtoResponse = gson.fromJson(jsonRemoveCandidateDtoResponce, RemoveCandidateDtoResponse.class);
        assertEquals("ok", removeCandidateDtoResponse.getStatus());
    }

    @Test
    @SuppressWarnings("unused")
    public void testAddCandidateWithError() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonRequest2 = gson.toJson(request2);
        String jsonRequest3 = gson.toJson(request3);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        server.registerVoter(jsonRequest2);
        server.registerVoter(jsonRequest3);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);

        VoterListDtoRequest voterListDtoRequest = new VoterListDtoRequest(response1.getToken());
        String jsonVoterListRequest = gson.toJson(voterListDtoRequest);
        String jsonVoterListResponce = server.getVoterList(jsonVoterListRequest);
        VoterListDtoResponse voterListDtoResponse = gson.fromJson(jsonVoterListResponce, VoterListDtoResponse.class);
        List<Voter> list1 = voterListDtoResponse.getVoterList();
        assertEquals(3, list1.size());

        sortVoterList(list1);
        Voter voter1 = list1.get(0);

        AddCandidateDtoRequest addCandidateDtoRequest1 = new AddCandidateDtoRequest(response1.getToken(), voter1.getId());
        String jsonAddCandidateRequest1 = gson.toJson(addCandidateDtoRequest1);
        String jsonAddCandidateResponce1 = server.addCandidate(jsonAddCandidateRequest1);
        AddCandidateDtoRequest addDuplicateCandidateDtoRequest = new AddCandidateDtoRequest(response1.getToken(), voter1.getId());
        String jsonAddDuplicateCandidateRequest = gson.toJson(addDuplicateCandidateDtoRequest);
        String jsonAddDuplicateCandidateResponce = server.addCandidate(jsonAddDuplicateCandidateRequest);
        ElectionErrorDtoResponse errorResponse = gson.fromJson(jsonAddDuplicateCandidateResponce, ElectionErrorDtoResponse.class);
        assertEquals(ElectionErrorCode.DUPLICATE_CANDIDATE.getErrorString(), errorResponse.getError());
    }

    @Test
    public void testAddRemoveProposalAndRating() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonRequest2 = gson.toJson(request2);
        String jsonRequest3 = gson.toJson(request3);
        String jsonRequest4 = gson.toJson(request4);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        String jsonResponse2 = server.registerVoter(jsonRequest2);
        String jsonResponse3 = server.registerVoter(jsonRequest3);
        String jsonResponse4 = server.registerVoter(jsonRequest4);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response2 = gson.fromJson(jsonResponse2, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response3 = gson.fromJson(jsonResponse3, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response4 = gson.fromJson(jsonResponse4, RegisterVoterDtoResponse.class);
        AddProposalDtoRequest addProposalDtoRequest11 = new AddProposalDtoRequest(response1.getToken(), "Прекратить жестокое обращение с летучими мышами на рок-фестивалях");
        AddProposalDtoRequest addProposalDtoRequest12 = new AddProposalDtoRequest(response1.getToken(), "Оградить детей от пагубного влияния алкоголя и табакокурения");
        AddProposalDtoRequest addProposalDtoRequest21 = new AddProposalDtoRequest(response2.getToken(), "Мир всем");
        AddProposalDtoRequest addProposalDtoRequest31 = new AddProposalDtoRequest(response3.getToken(), "Переснять Терминатор-2");
        String jsonAddProposalRequest11 = gson.toJson(addProposalDtoRequest11);
        String jsonAddProposalResponse11 = server.addProposal(jsonAddProposalRequest11);
        String jsonAddProposalRequest12 = gson.toJson(addProposalDtoRequest12);
        String jsonAddProposalResponse12 = server.addProposal(jsonAddProposalRequest12);
        String jsonAddProposalRequest21 = gson.toJson(addProposalDtoRequest21);
        String jsonAddProposalResponse21 = server.addProposal(jsonAddProposalRequest21);
        String jsonAddProposalRequest31 = gson.toJson(addProposalDtoRequest31);
        String jsonAddProposalResponse31 = server.addProposal(jsonAddProposalRequest31);
        AddProposalDtoResponse addProposalDtoResponse11 = gson.fromJson(jsonAddProposalResponse11, AddProposalDtoResponse.class);
        AddProposalDtoResponse addProposalDtoResponse12 = gson.fromJson(jsonAddProposalResponse12, AddProposalDtoResponse.class);
        AddProposalDtoResponse addProposalDtoResponse21 = gson.fromJson(jsonAddProposalResponse21, AddProposalDtoResponse.class);
        AddProposalDtoResponse addProposalDtoResponse31 = gson.fromJson(jsonAddProposalResponse31, AddProposalDtoResponse.class);
        assertEquals("ok", addProposalDtoResponse11.getStatus());
        assertEquals("ok", addProposalDtoResponse12.getStatus());
        assertEquals("ok", addProposalDtoResponse21.getStatus());
        assertEquals("ok", addProposalDtoResponse31.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest1 = new ProposalListWithRatingDtoRequest(response4.getToken());
        String jsonProposalListWithRatingDtoRequest1 = gson.toJson(proposalListWithRatingDtoRequest1);
        String jsonProposalListWithRatingDtoResponse1 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest1);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse1 = gson.fromJson(jsonProposalListWithRatingDtoResponse1, ProposalListWithRatingDtoResponse.class);
        int size1 = proposalListWithRatingDtoResponse1.getProposalListWithRating().size();
        assertEquals(4, size1);

        AddProposalRatingDtoRequest addProposalRatingDtoRequest11 = new AddProposalRatingDtoRequest(response4.getToken(), proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle(), 4);
        String jsonAddProposalRatingDtoRequest11 = gson.toJson(addProposalRatingDtoRequest11);
        String jsonAddProposalRatingDtoResponse11 = server.addProposalRating(jsonAddProposalRatingDtoRequest11);
        AddProposalRatingDtoResponse addProposalRatingDtoResponse11 = gson.fromJson(jsonAddProposalRatingDtoResponse11, AddProposalRatingDtoResponse.class);
        assertEquals("ok", addProposalRatingDtoResponse11.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest2 = new ProposalListWithRatingDtoRequest(response4.getToken());
        String jsonProposalListWithRatingDtoRequest2 = gson.toJson(proposalListWithRatingDtoRequest2);
        String jsonProposalListWithRatingDtoResponse2 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest2);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse2 = gson.fromJson(jsonProposalListWithRatingDtoResponse2, ProposalListWithRatingDtoResponse.class);
        int size2 = proposalListWithRatingDtoResponse2.getProposalListWithRating().size();
        assertEquals(4, size2);
        assertEquals(4.5, proposalListWithRatingDtoResponse2.getProposalListWithRating().get(size2 - 1).getAvgRating(), 0);

        RemoveProposalRatingDtoRequest removeProposalRatingDtoRequest = new RemoveProposalRatingDtoRequest(response4.getToken(), proposalListWithRatingDtoResponse2.getProposalListWithRating().get(3).getProposalTitle());
        String jsonRemoveProposalRatingDtoRequest = gson.toJson(removeProposalRatingDtoRequest);
        String jsonRemoveProposalRatingDtoResponse = server.removeProposalRating(jsonRemoveProposalRatingDtoRequest);
        RemoveProposalRatingDtoResponse removeProposalRatingDtoResponse = gson.fromJson(jsonRemoveProposalRatingDtoResponse, RemoveProposalRatingDtoResponse.class);
        assertEquals("ok", removeProposalRatingDtoResponse.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest3 = new ProposalListWithRatingDtoRequest(response4.getToken());
        String jsonProposalListWithRatingDtoRequest3 = gson.toJson(proposalListWithRatingDtoRequest3);
        String jsonProposalListWithRatingDtoResponse3 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest3);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse3 = gson.fromJson(jsonProposalListWithRatingDtoResponse3, ProposalListWithRatingDtoResponse.class);
        int size3 = proposalListWithRatingDtoResponse3.getProposalListWithRating().size();
        assertEquals(4, size3);
        assertEquals(5.0, proposalListWithRatingDtoResponse3.getProposalListWithRating().get(size3 - 1).getAvgRating(), 0);

        ProposalListByAllVotersDtoRequest proposalListByAllVotersDtoRequest = new ProposalListByAllVotersDtoRequest(response2.getToken());
        String jsonProposalListByAllVotersDtoRequest = gson.toJson(proposalListByAllVotersDtoRequest);
        String jsonProposalListByAllVotersDtoResponse = server.getProposalListByAllVoters(jsonProposalListByAllVotersDtoRequest);
        ProposalListByAllVotersDtoResponse proposalListByAllVotersDtoResponse = gson.fromJson(jsonProposalListByAllVotersDtoResponse, ProposalListByAllVotersDtoResponse.class);

        List<Voter> listWithProposals = proposalListByAllVotersDtoResponse.getProposalListByVoters();
        assertEquals(4, listWithProposals.size());

        sortVoterList(listWithProposals);

        VoterListDtoRequest voterListDtoRequest = new VoterListDtoRequest(response2.getToken());
        String jsonVoterListRequest = gson.toJson(voterListDtoRequest);
        String jsonVoterListResponce = server.getVoterList(jsonVoterListRequest);
        VoterListDtoResponse voterListDtoResponse = gson.fromJson(jsonVoterListResponce, VoterListDtoResponse.class);
        List<Voter> list1 = voterListDtoResponse.getVoterList();
        assertEquals(4, list1.size());

        sortVoterList(list1);
        Voter voter1 = list1.get(0);
        Voter voter2 = list1.get(1);
        Voter voter3 = list1.get(2);
        Voter voter4 = list1.get(3);

        ProposalListByVoterDtoRequest proposalListByVoterDtoRequest1 = new ProposalListByVoterDtoRequest(response2.getToken(), voter1.getId());
        String jsonProposalListByVoterDtoRequest1 = gson.toJson(proposalListByVoterDtoRequest1);
        String jsonProposalListByVoterDtoResponse1 = server.getProposalListByVoter(jsonProposalListByVoterDtoRequest1);
        ProposalListByVoterDtoResponse proposalListByVoterDtoResponse1 = gson.fromJson(jsonProposalListByVoterDtoResponse1, ProposalListByVoterDtoResponse.class);

        ProposalListByVoterDtoRequest proposalListByVoterDtoRequest2 = new ProposalListByVoterDtoRequest(response2.getToken(), voter2.getId());
        String jsonProposalListByVoterDtoRequest2 = gson.toJson(proposalListByVoterDtoRequest2);
        String jsonProposalListByVoterDtoResponse2 = server.getProposalListByVoter(jsonProposalListByVoterDtoRequest2);
        ProposalListByVoterDtoResponse proposalListByVoterDtoResponse2 = gson.fromJson(jsonProposalListByVoterDtoResponse2, ProposalListByVoterDtoResponse.class);

        ProposalListByVoterDtoRequest proposalListByVoterDtoRequest3 = new ProposalListByVoterDtoRequest(response2.getToken(), voter3.getId());
        String jsonProposalListByVoterDtoRequest3 = gson.toJson(proposalListByVoterDtoRequest3);
        String jsonProposalListByVoterDtoResponse3 = server.getProposalListByVoter(jsonProposalListByVoterDtoRequest3);
        ProposalListByVoterDtoResponse proposalListByVoterDtoResponse3 = gson.fromJson(jsonProposalListByVoterDtoResponse3, ProposalListByVoterDtoResponse.class);

        ProposalListByVoterDtoRequest proposalListByVoterDtoRequest4 = new ProposalListByVoterDtoRequest(response2.getToken(), voter4.getId());
        String jsonProposalListByVoterDtoRequest4 = gson.toJson(proposalListByVoterDtoRequest4);
        String jsonProposalListByVoterDtoResponse4 = server.getProposalListByVoter(jsonProposalListByVoterDtoRequest4);
        ProposalListByVoterDtoResponse proposalListByVoterDtoResponse4 = gson.fromJson(jsonProposalListByVoterDtoResponse4, ProposalListByVoterDtoResponse.class);

        assertEquals(listWithProposals.get(0).getFirstName(), proposalListByVoterDtoResponse1.getVoterWithProposals().getFirstName());
        assertEquals(listWithProposals.get(0).getLastName(), proposalListByVoterDtoResponse1.getVoterWithProposals().getLastName());
        assertEquals(listWithProposals.get(0).getPatronymic(), proposalListByVoterDtoResponse1.getVoterWithProposals().getPatronymic());
        assertEquals(listWithProposals.get(0).getProposalList().size(), proposalListByVoterDtoResponse1.getVoterWithProposals().getProposalList().size());
        assertEquals(listWithProposals.get(1).getFirstName(), proposalListByVoterDtoResponse2.getVoterWithProposals().getFirstName());
        assertEquals(listWithProposals.get(1).getLastName(), proposalListByVoterDtoResponse2.getVoterWithProposals().getLastName());
        assertEquals(listWithProposals.get(1).getPatronymic(), proposalListByVoterDtoResponse2.getVoterWithProposals().getPatronymic());
        assertEquals(listWithProposals.get(1).getProposalList().size(), proposalListByVoterDtoResponse2.getVoterWithProposals().getProposalList().size());
        assertEquals(listWithProposals.get(2).getFirstName(), proposalListByVoterDtoResponse3.getVoterWithProposals().getFirstName());
        assertEquals(listWithProposals.get(2).getLastName(), proposalListByVoterDtoResponse3.getVoterWithProposals().getLastName());
        assertEquals(listWithProposals.get(2).getPatronymic(), proposalListByVoterDtoResponse3.getVoterWithProposals().getPatronymic());
        assertEquals(listWithProposals.get(2).getProposalList().size(), proposalListByVoterDtoResponse3.getVoterWithProposals().getProposalList().size());
        assertEquals(listWithProposals.get(3).getFirstName(), proposalListByVoterDtoResponse4.getVoterWithProposals().getFirstName());
        assertEquals(listWithProposals.get(3).getLastName(), proposalListByVoterDtoResponse4.getVoterWithProposals().getLastName());
        assertEquals(listWithProposals.get(3).getPatronymic(), proposalListByVoterDtoResponse4.getVoterWithProposals().getPatronymic());
        assertEquals(listWithProposals.get(3).getProposalList().size(), proposalListByVoterDtoResponse4.getVoterWithProposals().getProposalList().size());
    }

    @Test
    @SuppressWarnings("unused")
    public void testAddRemoveProposalAndRatingValidation() {
        List<AddProposalDtoRequest> requestList1 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList1 = new ArrayList<>();
        String jsonRequest1 = gson.toJson(request1);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        String jsonRequest2 = gson.toJson(request2);
        String jsonResponse2 = server.registerVoter(jsonRequest2);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response2 = gson.fromJson(jsonResponse2, RegisterVoterDtoResponse.class);
        requestList1.add(new AddProposalDtoRequest(null, "Прекратить жестокое обращение с летучими мышами на рок-фестивалях"));
        requestList1.add(new AddProposalDtoRequest("123as562", "Прекратить жестокое обращение с летучими мышами на рок-фестивалях"));
        requestList1.add(new AddProposalDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572", "Прекратить жестокое обращение с летучими мышами на рок-фестивалях"));
        requestList1.add(new AddProposalDtoRequest(response1.getToken(), null));
        requestList1.add(new AddProposalDtoRequest(response1.getToken(), ""));
        requestList1.add(new AddProposalDtoRequest(response1.getToken(), "Abracadabra"));
        for (AddProposalDtoRequest request : requestList1) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.addProposal(jsonRequest);
            responseList1.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }

        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList1.get(i).getError());
        }
        for (int i = 3; i <= 5; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_PROPOSAL.getErrorString(), responseList1.get(i).getError());
        }

        AddProposalDtoRequest addProposalDtoRequest11 = new AddProposalDtoRequest(response1.getToken(), "Прекратить жестокое обращение с летучими мышами на рок-фестивалях");
        AddProposalDtoRequest addProposalDtoRequest12 = new AddProposalDtoRequest(response1.getToken(), "Оградить детей от пагубного влияния алкоголя и табакокурения");
        String jsonAddProposalRequest11 = gson.toJson(addProposalDtoRequest11);
        server.addProposal(jsonAddProposalRequest11);
        String jsonAddProposalRequest12 = gson.toJson(addProposalDtoRequest12);
        server.addProposal(jsonAddProposalRequest12);

        List<AddProposalRatingDtoRequest> requestList2 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList2 = new ArrayList<>();
        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest1 = new ProposalListWithRatingDtoRequest(response1.getToken());
        String jsonProposalListWithRatingDtoRequest1 = gson.toJson(proposalListWithRatingDtoRequest1);
        String jsonProposalListWithRatingDtoResponse1 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest1);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse1 = gson.fromJson(jsonProposalListWithRatingDtoResponse1, ProposalListWithRatingDtoResponse.class);

        requestList2.add(new AddProposalRatingDtoRequest(null, proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle(), 4));
        requestList2.add(new AddProposalRatingDtoRequest("123as562", proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle(), 4));
        requestList2.add(new AddProposalRatingDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572", proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle(), 4));
        requestList2.add(new AddProposalRatingDtoRequest(response1.getToken(), null, 4));
        requestList2.add(new AddProposalRatingDtoRequest(response1.getToken(), "", 4));
        requestList2.add(new AddProposalRatingDtoRequest(response1.getToken(), "Abracadabra", 4));
        requestList2.add(new AddProposalRatingDtoRequest(response1.getToken(), proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle(), 0));
        requestList2.add(new AddProposalRatingDtoRequest(response1.getToken(), proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle(), 6));

        for (AddProposalRatingDtoRequest request : requestList2) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.addProposalRating(jsonRequest);
            responseList2.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList2.get(i).getError());
        }
        for (int i = 3; i <= 5; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_PROPOSAL.getErrorString(), responseList2.get(i).getError());
        }
        for (int i = 6; i <= 7; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_RATING.getErrorString(), responseList2.get(i).getError());
        }

        AddProposalRatingDtoRequest addProposalRatingDtoRequest11 = new AddProposalRatingDtoRequest(response1.getToken(), proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle(), 4);
        String jsonAddProposalRatingDtoRequest11 = gson.toJson(addProposalRatingDtoRequest11);
        String jsonAddProposalRatingDtoResponse11 = server.addProposalRating(jsonAddProposalRatingDtoRequest11);
        gson.fromJson(jsonAddProposalRatingDtoResponse11, AddProposalRatingDtoResponse.class);

        List<RemoveProposalRatingDtoRequest> requestList3 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList3 = new ArrayList<>();

        requestList3.add(new RemoveProposalRatingDtoRequest(null, proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle()));
        requestList3.add(new RemoveProposalRatingDtoRequest("123as562", proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle()));
        requestList3.add(new RemoveProposalRatingDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572", proposalListWithRatingDtoResponse1.getProposalListWithRating().get(1).getProposalTitle()));
        requestList3.add(new RemoveProposalRatingDtoRequest(response1.getToken(), null));
        requestList3.add(new RemoveProposalRatingDtoRequest(response1.getToken(), ""));
        requestList3.add(new RemoveProposalRatingDtoRequest(response1.getToken(), "Abracadabra"));
        for (RemoveProposalRatingDtoRequest request : requestList3) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.addProposal(jsonRequest);
            responseList3.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList3.get(i).getError());
        }
        for (int i = 3; i <= 5; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_PROPOSAL.getErrorString(), responseList3.get(i).getError());
        }
    }

    @Test
    public void testAddRemoveProposalAndRatingWithError() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);
        String proposalTitle1 = "Прекратить жестокое обращение с летучими мышами на рок-фестивалях";
        AddProposalDtoRequest addProposalDtoRequest11 = new AddProposalDtoRequest(response1.getToken(), proposalTitle1);
        AddProposalDtoRequest addProposalDtoRequest12 = new AddProposalDtoRequest(response1.getToken(), proposalTitle1);
        String jsonAddProposalRequest11 = gson.toJson(addProposalDtoRequest11);
        String jsonAddProposalResponse11 = server.addProposal(jsonAddProposalRequest11);
        String jsonAddProposalRequest12 = gson.toJson(addProposalDtoRequest12);
        String jsonAddProposalResponse12 = server.addProposal(jsonAddProposalRequest12);
        AddProposalDtoResponse addProposalDtoResponse11 = gson.fromJson(jsonAddProposalResponse11, AddProposalDtoResponse.class);
        assertEquals("ok", addProposalDtoResponse11.getStatus());
        ElectionErrors addProposalDtoResponse12 = gson.fromJson(jsonAddProposalResponse12, ElectionErrors.class);
        assertEquals(ElectionErrorCode.DUPLICATE_PROPOSAL.getErrorString(), addProposalDtoResponse12.getError());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest1 = new ProposalListWithRatingDtoRequest(response1.getToken());
        String jsonProposalListWithRatingDtoRequest1 = gson.toJson(proposalListWithRatingDtoRequest1);
        String jsonProposalListWithRatingDtoResponse1 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest1);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse1 = gson.fromJson(jsonProposalListWithRatingDtoResponse1, ProposalListWithRatingDtoResponse.class);

        int index1 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse1.getProposalListWithRating(), proposalTitle1);

        AddProposalRatingDtoRequest addProposalRatingDtoRequest11 = new AddProposalRatingDtoRequest(response1.getToken(), proposalListWithRatingDtoResponse1.getProposalListWithRating().get(index1).getProposalTitle(), 4);
        String jsonAddProposalRatingDtoRequest11 = gson.toJson(addProposalRatingDtoRequest11);
        String jsonAddProposalRatingDtoResponse11 = server.addProposalRating(jsonAddProposalRatingDtoRequest11);
        ElectionErrors addProposalRatingDtoResponse11 = gson.fromJson(jsonAddProposalRatingDtoResponse11, ElectionErrors.class);
        assertEquals(ElectionErrorCode.CANT_EDIT_OWN_RATING.getErrorString(), addProposalRatingDtoResponse11.getError());

        String jsonRequest2 = gson.toJson(request2);
        String jsonResponse2 = server.registerVoter(jsonRequest2);
        RegisterVoterDtoResponse response2 = gson.fromJson(jsonResponse2, RegisterVoterDtoResponse.class);
        String proposalTitle2 = "Мир всем";
        AddProposalDtoRequest addProposalDtoRequest21 = new AddProposalDtoRequest(response2.getToken(), proposalTitle2);
        String jsonAddProposalRequest21 = gson.toJson(addProposalDtoRequest21);
        String jsonAddProposalResponse21 = server.addProposal(jsonAddProposalRequest21);
        AddProposalDtoResponse addProposalDtoResponse21 = gson.fromJson(jsonAddProposalResponse21, AddProposalDtoResponse.class);
        assertEquals("ok", addProposalDtoResponse21.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest2 = new ProposalListWithRatingDtoRequest(response1.getToken());
        String jsonProposalListWithRatingDtoRequest2 = gson.toJson(proposalListWithRatingDtoRequest2);
        String jsonProposalListWithRatingDtoResponse2 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest2);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse2 = gson.fromJson(jsonProposalListWithRatingDtoResponse2, ProposalListWithRatingDtoResponse.class);

        int index2 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse2.getProposalListWithRating(), proposalTitle1);

        RemoveProposalRatingDtoRequest removeProposalRatingDtoRequest1 = new RemoveProposalRatingDtoRequest(response1.getToken(), proposalListWithRatingDtoResponse2.getProposalListWithRating().get(index2).getProposalTitle());
        String jsonRemoveProposalRatingDtoRequest1 = gson.toJson(removeProposalRatingDtoRequest1);
        String jsonRemoveProposalRatingDtoResponse1 = server.removeProposalRating(jsonRemoveProposalRatingDtoRequest1);
        ElectionErrors removeProposalRatingDtoResponse1 = gson.fromJson(jsonRemoveProposalRatingDtoResponse1, ElectionErrors.class);
        assertEquals(ElectionErrorCode.CANT_REMOVE_OWN_RATING.getErrorString(), removeProposalRatingDtoResponse1.getError());

        int index3 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse2.getProposalListWithRating(), proposalTitle2);

        RemoveProposalRatingDtoRequest removeProposalRatingDtoRequest2 = new RemoveProposalRatingDtoRequest(response1.getToken(), proposalListWithRatingDtoResponse2.getProposalListWithRating().get(index3).getProposalTitle());
        String jsonRemoveProposalRatingDtoRequest2 = gson.toJson(removeProposalRatingDtoRequest2);
        String jsonRemoveProposalRatingDtoResponse2 = server.removeProposalRating(jsonRemoveProposalRatingDtoRequest2);
        ElectionErrors removeProposalRatingDtoResponse2 = gson.fromJson(jsonRemoveProposalRatingDtoResponse2, ElectionErrors.class);
        assertEquals(ElectionErrorCode.NO_RATING.getErrorString(), removeProposalRatingDtoResponse2.getError());
    }

    @Test
    public void testProposalListsValidation() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonResponce1 = server.registerVoter(jsonRequest1);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponce1, RegisterVoterDtoResponse.class);
        AddProposalDtoRequest addProposalDtoRequest11 = new AddProposalDtoRequest(response1.getToken(), "Прекратить жестокое обращение с летучими мышами на рок-фестивалях");
        String jsonAddProposalRequest11 = gson.toJson(addProposalDtoRequest11);
        server.addProposal(jsonAddProposalRequest11);

        List<ProposalListWithRatingDtoRequest> requestList1 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList1 = new ArrayList<>();
        requestList1.add(new ProposalListWithRatingDtoRequest(null));
        requestList1.add(new ProposalListWithRatingDtoRequest("123as562"));
        requestList1.add(new ProposalListWithRatingDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572"));
        for (ProposalListWithRatingDtoRequest request : requestList1) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.getProposalListWithRating(jsonRequest);
            responseList1.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList1.get(i).getError());
        }
        List<ProposalListByAllVotersDtoRequest> requestList2 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList2 = new ArrayList<>();
        requestList2.add(new ProposalListByAllVotersDtoRequest(null));
        requestList2.add(new ProposalListByAllVotersDtoRequest("123as562"));
        requestList2.add(new ProposalListByAllVotersDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572"));
        for (ProposalListByAllVotersDtoRequest request : requestList2) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.getProposalListByAllVoters(jsonRequest);
            responseList2.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList2.get(i).getError());
        }

        List<ProposalListByVoterDtoRequest> requestList3 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList3 = new ArrayList<>();
        requestList3.add(new ProposalListByVoterDtoRequest(null, 0));
        requestList3.add(new ProposalListByVoterDtoRequest("123as562", 0));
        requestList3.add(new ProposalListByVoterDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572", 0));
        for (ProposalListByVoterDtoRequest request : requestList3) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.getProposalListByAllVoters(jsonRequest);
            responseList3.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList3.get(i).getError());
        }
    }

    @Test
    public void testProgramme() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonRequest2 = gson.toJson(request2);
        String jsonRequest3 = gson.toJson(request3);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        String jsonResponse2 = server.registerVoter(jsonRequest2);
        String jsonResponse3 = server.registerVoter(jsonRequest3);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response2 = gson.fromJson(jsonResponse2, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response3 = gson.fromJson(jsonResponse3, RegisterVoterDtoResponse.class);

        String proposalTitle1 = "Прекратить жестокое обращение с летучими мышами на рок-фестивалях";
        String proposalTitle2 = "Оградить детей от пагубного влияния алкоголя и табакокурения";
        String proposalTitle3 = "Мир всем";
        String proposalTitle4 = "Переснять Терминатор-2";

        AddProposalDtoRequest addProposalDtoRequest11 = new AddProposalDtoRequest(response1.getToken(), proposalTitle1);
        AddProposalDtoRequest addProposalDtoRequest12 = new AddProposalDtoRequest(response1.getToken(), proposalTitle2);
        AddProposalDtoRequest addProposalDtoRequest21 = new AddProposalDtoRequest(response2.getToken(), proposalTitle3);
        AddProposalDtoRequest addProposalDtoRequest31 = new AddProposalDtoRequest(response3.getToken(), proposalTitle4);
        String jsonAddProposalRequest11 = gson.toJson(addProposalDtoRequest11);
        String jsonAddProposalResponse11 = server.addProposal(jsonAddProposalRequest11);
        String jsonAddProposalRequest12 = gson.toJson(addProposalDtoRequest12);
        String jsonAddProposalResponse12 = server.addProposal(jsonAddProposalRequest12);
        String jsonAddProposalRequest21 = gson.toJson(addProposalDtoRequest21);
        String jsonAddProposalResponse21 = server.addProposal(jsonAddProposalRequest21);
        String jsonAddProposalRequest31 = gson.toJson(addProposalDtoRequest31);
        String jsonAddProposalResponse31 = server.addProposal(jsonAddProposalRequest31);
        gson.fromJson(jsonAddProposalResponse11, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse12, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse21, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse31, AddProposalDtoResponse.class);

        VoterListDtoRequest voterListDtoRequest = new VoterListDtoRequest(response1.getToken());
        String jsonVoterListRequest = gson.toJson(voterListDtoRequest);
        String jsonVoterListResponce = server.getVoterList(jsonVoterListRequest);
        VoterListDtoResponse voterListDtoResponse = gson.fromJson(jsonVoterListResponce, VoterListDtoResponse.class);
        List<Voter> list1 = voterListDtoResponse.getVoterList();
        assertEquals(3, list1.size());

        sortVoterList(list1);
        Voter voter1 = list1.get(1);

        AddCandidateDtoRequest addCandidateDtoRequest1 = new AddCandidateDtoRequest(response1.getToken(), voter1.getId());
        String jsonAddCandidateRequest1 = gson.toJson(addCandidateDtoRequest1);
        String jsonAddCandidateResponce1 = server.addCandidate(jsonAddCandidateRequest1);
        AddCandidateDtoResponse addCandidateDtoResponse1 = gson.fromJson(jsonAddCandidateResponce1, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse1.getStatus());

        Voter voter2 = list1.get(2);

        AddCandidateDtoRequest addCandidateDtoRequest2 = new AddCandidateDtoRequest(response2.getToken(), voter2.getId());
        String jsonAddCandidateRequest2 = gson.toJson(addCandidateDtoRequest2);
        String jsonAddCandidateResponce2 = server.addCandidate(jsonAddCandidateRequest2);
        AddCandidateDtoResponse addCandidateDtoResponse2 = gson.fromJson(jsonAddCandidateResponce2, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse2.getStatus());

        CandidateAgreeDtoRequest candidateAgreeDtoRequest = new CandidateAgreeDtoRequest(response2.getToken(), true);
        String jsonCandidateAgreeDtoRequest = gson.toJson(candidateAgreeDtoRequest);
        String jsonCandidateAgreeDtoResponce = server.agree(jsonCandidateAgreeDtoRequest);
        CandidateAgreeDtoResponse candidateAgreeDtoResponse = gson.fromJson(jsonCandidateAgreeDtoResponce, CandidateAgreeDtoResponse.class);
        assertEquals("ok", candidateAgreeDtoResponse.getStatus());

        CandidateListDtoRequest candidateListDtoRequest21 = new CandidateListDtoRequest(response2.getToken());
        String jsonCandidateListDtoRequest21 = gson.toJson(candidateListDtoRequest21);
        String jsonCandidateListDtoResponse21 = server.getCandidateList(jsonCandidateListDtoRequest21);
        CandidateListDtoResponse candidateListDtoResponse21 = gson.fromJson(jsonCandidateListDtoResponse21, CandidateListDtoResponse.class);
        assertEquals(1, candidateListDtoResponse21.getCandidateList().size());

        assertEquals(1, candidateListDtoResponse21.getCandidateList().get(0).getProgramme().getProgrammeProposalList().size());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest = new ProposalListWithRatingDtoRequest(response2.getToken());
        String jsonProposalListWithRatingDtoRequest = gson.toJson(proposalListWithRatingDtoRequest);
        String jsonProposalListWithRatingDtoResponse = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse = gson.fromJson(jsonProposalListWithRatingDtoResponse, ProposalListWithRatingDtoResponse.class);

        int index1 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse.getProposalListWithRating(), proposalTitle1);

        AddProposalToProgrammeDtoRequest addProposalToProgrammeDtoRequest = new AddProposalToProgrammeDtoRequest(response2.getToken(), proposalListWithRatingDtoResponse.getProposalListWithRating().get(index1).getProposalTitle());
        String jsonAddProposalToProgrammeDtoRequest = gson.toJson(addProposalToProgrammeDtoRequest);
        String jsonAddProposalToProgrammeDtoResponse = server.addProposalToProgramme(jsonAddProposalToProgrammeDtoRequest);
        AddProposalToProgrammeDtoResponse addProposalToProgrammeDtoResponse = gson.fromJson(jsonAddProposalToProgrammeDtoResponse, AddProposalToProgrammeDtoResponse.class);
        assertEquals("ok", addProposalToProgrammeDtoResponse.getStatus());

        OwnProgrammeDtoRequest ownProgrammeDtoRequest1 = new OwnProgrammeDtoRequest(response2.getToken());
        String jsonOwnProgrammeDtoRequest1 = gson.toJson(ownProgrammeDtoRequest1);
        String jsonOwnProgrammeDtoResponse1 = server.getOwnProgramme(jsonOwnProgrammeDtoRequest1);
        OwnProgrammeDtoResponse ownProgrammeDtoResponse1 = gson.fromJson(jsonOwnProgrammeDtoResponse1, OwnProgrammeDtoResponse.class);
        assertEquals(2, ownProgrammeDtoResponse1.getProgramme().size());

        RemoveProposalFromProgrammeDtoRequest removeProposalFromProgrammeDtoRequest = new RemoveProposalFromProgrammeDtoRequest(response2.getToken(), ownProgrammeDtoResponse1.getProgramme().get(1).getProposalTitle());
        String jsonRemoveProposalFromProgrammeDtoRequest = gson.toJson(removeProposalFromProgrammeDtoRequest);
        String jsonRemoveProposalFromProgrammeDtoResponse = server.removeProposalFromProgramme(jsonRemoveProposalFromProgrammeDtoRequest);
        RemoveProposalFromProgrammeDtoResponse removeProposalFromProgrammeDtoResponse = gson.fromJson(jsonRemoveProposalFromProgrammeDtoResponse, RemoveProposalFromProgrammeDtoResponse.class);
        assertEquals("ok", removeProposalFromProgrammeDtoResponse.getStatus());

        OwnProgrammeDtoRequest ownProgrammeDtoRequest2 = new OwnProgrammeDtoRequest(response2.getToken());
        String jsonOwnProgrammeDtoRequest2 = gson.toJson(ownProgrammeDtoRequest2);
        String jsonOwnProgrammeDtoResponse2 = server.getOwnProgramme(jsonOwnProgrammeDtoRequest2);
        OwnProgrammeDtoResponse ownProgrammeDtoResponse2 = gson.fromJson(jsonOwnProgrammeDtoResponse2, OwnProgrammeDtoResponse.class);
        assertEquals(1, ownProgrammeDtoResponse2.getProgramme().size());

        CandidateListDtoRequest candidateListDtoRequest = new CandidateListDtoRequest(response2.getToken());
        String jsonCandidateListDtoRequest = gson.toJson(candidateListDtoRequest);
        String jsonCandidateListDtoResponse = server.getCandidateList(jsonCandidateListDtoRequest);
        CandidateListDtoResponse candidateListDtoResponse = gson.fromJson(jsonCandidateListDtoResponse, CandidateListDtoResponse.class);
        System.out.println(candidateListDtoResponse);

        List<CandidateList> candidateList1 = candidateListDtoResponse.getCandidateList();
        sortCandidateList(candidateList1);
        assertEquals(request2.getFirstName(), candidateListDtoResponse.getCandidateList().get(0).getFirstName());
        assertEquals(request2.getLastName(), candidateListDtoResponse.getCandidateList().get(0).getLastName());
        assertEquals(request2.getPatronymic(), candidateListDtoResponse.getCandidateList().get(0).getPatronymic());
        assertEquals(1, candidateListDtoResponse.getCandidateList().get(0).getAgree());
        assertEquals(1, candidateListDtoResponse.getCandidateList().get(0).getProgramme().getProgrammeProposalList().size());
    }

    @Test
    public void testProgrammeValidation() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonRequest2 = gson.toJson(request2);
        String jsonRequest3 = gson.toJson(request3);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        String jsonResponse2 = server.registerVoter(jsonRequest2);
        String jsonResponse3 = server.registerVoter(jsonRequest3);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response2 = gson.fromJson(jsonResponse2, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response3 = gson.fromJson(jsonResponse3, RegisterVoterDtoResponse.class);

        AddProposalDtoRequest addProposalDtoRequest11 = new AddProposalDtoRequest(response1.getToken(), "Прекратить жестокое обращение с летучими мышами на рок-фестивалях");
        AddProposalDtoRequest addProposalDtoRequest12 = new AddProposalDtoRequest(response1.getToken(), "Оградить детей от пагубного влияния алкоголя и табакокурения");
        AddProposalDtoRequest addProposalDtoRequest21 = new AddProposalDtoRequest(response2.getToken(), "Мир всем");
        AddProposalDtoRequest addProposalDtoRequest31 = new AddProposalDtoRequest(response3.getToken(), "Переснять Терминатор-2");
        String jsonAddProposalRequest11 = gson.toJson(addProposalDtoRequest11);
        String jsonAddProposalResponse11 = server.addProposal(jsonAddProposalRequest11);
        String jsonAddProposalRequest12 = gson.toJson(addProposalDtoRequest12);
        String jsonAddProposalResponse12 = server.addProposal(jsonAddProposalRequest12);
        String jsonAddProposalRequest21 = gson.toJson(addProposalDtoRequest21);
        String jsonAddProposalResponse21 = server.addProposal(jsonAddProposalRequest21);
        String jsonAddProposalRequest31 = gson.toJson(addProposalDtoRequest31);
        String jsonAddProposalResponse31 = server.addProposal(jsonAddProposalRequest31);
        gson.fromJson(jsonAddProposalResponse11, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse12, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse21, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse31, AddProposalDtoResponse.class);

        VoterListDtoRequest voterListDtoRequest = new VoterListDtoRequest(response1.getToken());
        String jsonVoterListRequest = gson.toJson(voterListDtoRequest);
        String jsonVoterListResponce = server.getVoterList(jsonVoterListRequest);
        VoterListDtoResponse voterListDtoResponse = gson.fromJson(jsonVoterListResponce, VoterListDtoResponse.class);

        List<Voter> list1 = voterListDtoResponse.getVoterList();
        assertEquals(3, list1.size());

        sortVoterList(list1);
        Voter voter1 = list1.get(1);

        AddCandidateDtoRequest addCandidateDtoRequest1 = new AddCandidateDtoRequest(response1.getToken(), voter1.getId());
        String jsonAddCandidateRequest1 = gson.toJson(addCandidateDtoRequest1);
        String jsonAddCandidateResponce1 = server.addCandidate(jsonAddCandidateRequest1);
        AddCandidateDtoResponse addCandidateDtoResponse1 = gson.fromJson(jsonAddCandidateResponce1, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse1.getStatus());

        Voter voter2 = list1.get(2);

        AddCandidateDtoRequest addCandidateDtoRequest2 = new AddCandidateDtoRequest(response2.getToken(), voter2.getId());
        String jsonAddCandidateRequest2 = gson.toJson(addCandidateDtoRequest2);
        String jsonAddCandidateResponce2 = server.addCandidate(jsonAddCandidateRequest2);
        AddCandidateDtoResponse addCandidateDtoResponse2 = gson.fromJson(jsonAddCandidateResponce2, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse2.getStatus());

        CandidateAgreeDtoRequest candidateAgreeDtoRequest = new CandidateAgreeDtoRequest(response2.getToken(), true);
        String jsonCandidateAgreeDtoRequest = gson.toJson(candidateAgreeDtoRequest);
        String jsonCandidateAgreeDtoResponce = server.agree(jsonCandidateAgreeDtoRequest);
        CandidateAgreeDtoResponse candidateAgreeDtoResponse = gson.fromJson(jsonCandidateAgreeDtoResponce, CandidateAgreeDtoResponse.class);
        assertEquals("ok", candidateAgreeDtoResponse.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest = new ProposalListWithRatingDtoRequest(response2.getToken());
        String jsonProposalListWithRatingDtoRequest = gson.toJson(proposalListWithRatingDtoRequest);
        String jsonProposalListWithRatingDtoResponse = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse = gson.fromJson(jsonProposalListWithRatingDtoResponse, ProposalListWithRatingDtoResponse.class);

        List<AddProposalToProgrammeDtoRequest> requestList1 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList1 = new ArrayList<>();
        requestList1.add(new AddProposalToProgrammeDtoRequest(null, proposalListWithRatingDtoResponse.getProposalListWithRating().get(0).getProposalTitle()));
        requestList1.add(new AddProposalToProgrammeDtoRequest("123as562", proposalListWithRatingDtoResponse.getProposalListWithRating().get(0).getProposalTitle()));
        requestList1.add(new AddProposalToProgrammeDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572", proposalListWithRatingDtoResponse.getProposalListWithRating().get(0).getProposalTitle()));
        requestList1.add(new AddProposalToProgrammeDtoRequest(response2.getToken(), null));
        requestList1.add(new AddProposalToProgrammeDtoRequest(response2.getToken(), ""));
        requestList1.add(new AddProposalToProgrammeDtoRequest(response2.getToken(), "Abracadabra"));
        for (AddProposalToProgrammeDtoRequest request : requestList1) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.addProposalToProgramme(jsonRequest);
            responseList1.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList1.get(i).getError());
        }

        for (int i = 3; i <= 5; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_PROPOSAL.getErrorString(), responseList1.get(i).getError());
        }

        List<RemoveProposalFromProgrammeDtoRequest> requestList2 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList2 = new ArrayList<>();
        requestList2.add(new RemoveProposalFromProgrammeDtoRequest(null, proposalListWithRatingDtoResponse.getProposalListWithRating().get(0).getProposalTitle()));
        requestList2.add(new RemoveProposalFromProgrammeDtoRequest("123as562", proposalListWithRatingDtoResponse.getProposalListWithRating().get(0).getProposalTitle()));
        requestList2.add(new RemoveProposalFromProgrammeDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572", proposalListWithRatingDtoResponse.getProposalListWithRating().get(0).getProposalTitle()));
        requestList2.add(new RemoveProposalFromProgrammeDtoRequest(response2.getToken(), null));
        requestList2.add(new RemoveProposalFromProgrammeDtoRequest(response2.getToken(), ""));
        requestList2.add(new RemoveProposalFromProgrammeDtoRequest(response2.getToken(), "Abracadabra"));
        for (RemoveProposalFromProgrammeDtoRequest request : requestList2) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.removeProposalFromProgramme(jsonRequest);
            responseList2.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList2.get(i).getError());
        }

        for (int i = 3; i <= 5; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_PROPOSAL.getErrorString(), responseList2.get(i).getError());
        }

        List<CandidateListDtoRequest> requestList3 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList3 = new ArrayList<>();
        requestList3.add(new CandidateListDtoRequest(null));
        requestList3.add(new CandidateListDtoRequest("123as562"));
        requestList3.add(new CandidateListDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572"));
        for (CandidateListDtoRequest request : requestList3) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.removeProposalFromProgramme(jsonRequest);
            responseList3.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList3.get(i).getError());
        }

        List<OwnProgrammeDtoRequest> requestList4 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList4 = new ArrayList<>();
        requestList4.add(new OwnProgrammeDtoRequest(null));
        requestList4.add(new OwnProgrammeDtoRequest("123as562"));
        requestList4.add(new OwnProgrammeDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572"));
        for (OwnProgrammeDtoRequest request : requestList4) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.removeProposalFromProgramme(jsonRequest);
            responseList4.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList4.get(i).getError());
        }
    }

    @Test
    public void testProgrammeErrors() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonRequest2 = gson.toJson(request2);
        String jsonRequest3 = gson.toJson(request3);
        String jsonResponse1 = server.registerVoter(jsonRequest1);
        String jsonResponse2 = server.registerVoter(jsonRequest2);
        String jsonResponse3 = server.registerVoter(jsonRequest3);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponse1, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response2 = gson.fromJson(jsonResponse2, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response3 = gson.fromJson(jsonResponse3, RegisterVoterDtoResponse.class);

        String proposalTitle1 = "Прекратить жестокое обращение с летучими мышами на рок-фестивалях";
        String proposalTitle2 = "Оградить детей от пагубного влияния алкоголя и табакокурения";
        String proposalTitle3 = "Мир всем";
        String proposalTitle4 = "Переснять Терминатор-2";

        AddProposalDtoRequest addProposalDtoRequest11 = new AddProposalDtoRequest(response1.getToken(), proposalTitle1);
        AddProposalDtoRequest addProposalDtoRequest12 = new AddProposalDtoRequest(response1.getToken(), proposalTitle2);
        AddProposalDtoRequest addProposalDtoRequest21 = new AddProposalDtoRequest(response2.getToken(), proposalTitle3);
        AddProposalDtoRequest addProposalDtoRequest31 = new AddProposalDtoRequest(response3.getToken(), proposalTitle4);
        String jsonAddProposalRequest11 = gson.toJson(addProposalDtoRequest11);
        String jsonAddProposalResponse11 = server.addProposal(jsonAddProposalRequest11);
        String jsonAddProposalRequest12 = gson.toJson(addProposalDtoRequest12);
        String jsonAddProposalResponse12 = server.addProposal(jsonAddProposalRequest12);
        String jsonAddProposalRequest21 = gson.toJson(addProposalDtoRequest21);
        String jsonAddProposalResponse21 = server.addProposal(jsonAddProposalRequest21);
        String jsonAddProposalRequest31 = gson.toJson(addProposalDtoRequest31);
        String jsonAddProposalResponse31 = server.addProposal(jsonAddProposalRequest31);
        gson.fromJson(jsonAddProposalResponse11, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse12, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse21, AddProposalDtoResponse.class);
        gson.fromJson(jsonAddProposalResponse31, AddProposalDtoResponse.class);

        VoterListDtoRequest voterListDtoRequest = new VoterListDtoRequest(response1.getToken());
        String jsonVoterListRequest = gson.toJson(voterListDtoRequest);
        String jsonVoterListResponce = server.getVoterList(jsonVoterListRequest);
        VoterListDtoResponse voterListDtoResponse = gson.fromJson(jsonVoterListResponce, VoterListDtoResponse.class);

        List<Voter> list1 = voterListDtoResponse.getVoterList();
        assertEquals(3, list1.size());
        sortVoterList(list1);

        Voter voter1 = list1.get(1);

        AddCandidateDtoRequest addCandidateDtoRequest1 = new AddCandidateDtoRequest(response1.getToken(), voter1.getId());
        String jsonAddCandidateRequest1 = gson.toJson(addCandidateDtoRequest1);
        String jsonAddCandidateResponce1 = server.addCandidate(jsonAddCandidateRequest1);
        AddCandidateDtoResponse addCandidateDtoResponse1 = gson.fromJson(jsonAddCandidateResponce1, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse1.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest = new ProposalListWithRatingDtoRequest(response2.getToken());
        String jsonProposalListWithRatingDtoRequest = gson.toJson(proposalListWithRatingDtoRequest);
        String jsonProposalListWithRatingDtoResponse = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse = gson.fromJson(jsonProposalListWithRatingDtoResponse, ProposalListWithRatingDtoResponse.class);

        int index1 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse.getProposalListWithRating(), proposalTitle1);

        AddProposalToProgrammeDtoRequest addProposalToProgrammeDtoRequest1 = new AddProposalToProgrammeDtoRequest(response2.getToken(), proposalListWithRatingDtoResponse.getProposalListWithRating().get(index1).getProposalTitle());
        String jsonAddProposalToProgrammeDtoRequest1 = gson.toJson(addProposalToProgrammeDtoRequest1);
        String jsonAddProposalToProgrammeDtoResponse1 = server.addProposalToProgramme(jsonAddProposalToProgrammeDtoRequest1);
        ElectionErrors addProposalToProgrammeDtoResponse1 = gson.fromJson(jsonAddProposalToProgrammeDtoResponse1, ElectionErrors.class);
        assertEquals(ElectionErrorCode.DISAGREE.getErrorString(), addProposalToProgrammeDtoResponse1.getError());

        RemoveProposalFromProgrammeDtoRequest removeProposalToProgrammeDtoRequest1 = new RemoveProposalFromProgrammeDtoRequest(response2.getToken(), proposalListWithRatingDtoResponse.getProposalListWithRating().get(index1).getProposalTitle());
        String jsonRemoveProposalFromProgrammeDtoRequest1 = gson.toJson(removeProposalToProgrammeDtoRequest1);
        String jsonRemoveProposalFromProgrammeDtoResponse1 = server.removeProposalFromProgramme(jsonRemoveProposalFromProgrammeDtoRequest1);
        ElectionErrors removeProposalFromProgrammeDtoResponse1 = gson.fromJson(jsonRemoveProposalFromProgrammeDtoResponse1, ElectionErrors.class);
        assertEquals(ElectionErrorCode.DISAGREE.getErrorString(), removeProposalFromProgrammeDtoResponse1.getError());

        CandidateAgreeDtoRequest candidateAgreeDtoRequest = new CandidateAgreeDtoRequest(response2.getToken(), true);
        String jsonCandidateAgreeDtoRequest = gson.toJson(candidateAgreeDtoRequest);
        String jsonCandidateAgreeDtoResponce = server.agree(jsonCandidateAgreeDtoRequest);
        CandidateAgreeDtoResponse candidateAgreeDtoResponse = gson.fromJson(jsonCandidateAgreeDtoResponce, CandidateAgreeDtoResponse.class);
        assertEquals("ok", candidateAgreeDtoResponse.getStatus());

        AddProposalToProgrammeDtoRequest addProposalToProgrammeDtoRequest2 = new AddProposalToProgrammeDtoRequest(response2.getToken(), proposalListWithRatingDtoResponse.getProposalListWithRating().get(index1).getProposalTitle());
        String jsonAddProposalToProgrammeDtoRequest2 = gson.toJson(addProposalToProgrammeDtoRequest2);
        String jsonAddProposalToProgrammeDtoResponse2 = server.addProposalToProgramme(jsonAddProposalToProgrammeDtoRequest2);
        AddProposalToProgrammeDtoResponse addProposalToProgrammeDtoResponse2 = gson.fromJson(jsonAddProposalToProgrammeDtoResponse2, AddProposalToProgrammeDtoResponse.class);
        assertEquals("ok", addProposalToProgrammeDtoResponse2.getStatus());
        AddProposalToProgrammeDtoRequest addProposalToProgrammeDtoRequest3 = new AddProposalToProgrammeDtoRequest(response2.getToken(), proposalListWithRatingDtoResponse.getProposalListWithRating().get(index1).getProposalTitle());
        String jsonAddProposalToProgrammeDtoRequest3 = gson.toJson(addProposalToProgrammeDtoRequest3);
        String jsonAddProposalToProgrammeDtoResponse3 = server.addProposalToProgramme(jsonAddProposalToProgrammeDtoRequest3);
        ElectionErrors addProposalToProgrammeDtoResponse3 = gson.fromJson(jsonAddProposalToProgrammeDtoResponse3, ElectionErrors.class);
        assertEquals(ElectionErrorCode.DUPLICATE_PROPOSAL_IN_PROGRAMME.getErrorString(), addProposalToProgrammeDtoResponse3.getError());

        int index2 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse.getProposalListWithRating(), proposalTitle3);

        RemoveProposalFromProgrammeDtoRequest removeProposalToProgrammeDtoRequest2 = new RemoveProposalFromProgrammeDtoRequest(response2.getToken(), proposalListWithRatingDtoResponse.getProposalListWithRating().get(index2).getProposalTitle());
        String jsonRemoveProposalFromProgrammeDtoRequest2 = gson.toJson(removeProposalToProgrammeDtoRequest2);
        String jsonRemoveProposalFromProgrammeDtoResponse2 = server.removeProposalFromProgramme(jsonRemoveProposalFromProgrammeDtoRequest2);
        ElectionErrors removeProposalFromProgrammeDtoResponse2 = gson.fromJson(jsonRemoveProposalFromProgrammeDtoResponse2, ElectionErrors.class);
        assertEquals(ElectionErrorCode.CANT_REMOVE_OWN_PROPOSAL.getErrorString(), removeProposalFromProgrammeDtoResponse2.getError());
    }

    @Test
    @SuppressWarnings("unused")
    public void testTrueElections() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonRequest2 = gson.toJson(request2);
        String jsonRequest3 = gson.toJson(request3);
        String jsonRequest4 = gson.toJson(request4);
        String jsonRequest5 = gson.toJson(request5);
        String jsonRequest6 = gson.toJson(request6);
        String jsonRequest7 = gson.toJson(request7);
        String jsonRequest8 = gson.toJson(request8);
        String jsonRequest9 = gson.toJson(request9);
        String jsonRequest10 = gson.toJson(request10);
        String jsonResponce1 = server.registerVoter(jsonRequest1);
        String jsonResponce2 = server.registerVoter(jsonRequest2);
        String jsonResponce3 = server.registerVoter(jsonRequest3);
        String jsonResponce4 = server.registerVoter(jsonRequest4);
        String jsonResponce5 = server.registerVoter(jsonRequest5);
        String jsonResponce6 = server.registerVoter(jsonRequest6);
        String jsonResponce7 = server.registerVoter(jsonRequest7);
        String jsonResponce8 = server.registerVoter(jsonRequest8);
        String jsonResponce9 = server.registerVoter(jsonRequest9);
        String jsonResponce10 = server.registerVoter(jsonRequest10);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponce1, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response2 = gson.fromJson(jsonResponce2, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response3 = gson.fromJson(jsonResponce3, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response4 = gson.fromJson(jsonResponce4, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response5 = gson.fromJson(jsonResponce5, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response6 = gson.fromJson(jsonResponce6, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response7 = gson.fromJson(jsonResponce7, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response8 = gson.fromJson(jsonResponce8, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response9 = gson.fromJson(jsonResponce9, RegisterVoterDtoResponse.class);
        RegisterVoterDtoResponse response10 = gson.fromJson(jsonResponce10, RegisterVoterDtoResponse.class);

        VoterListDtoRequest voterListDtoRequest = new VoterListDtoRequest(response1.getToken());
        String jsonVoterListRequest = gson.toJson(voterListDtoRequest);
        String jsonVoterListResponce = server.getVoterList(jsonVoterListRequest);
        VoterListDtoResponse voterListDtoResponse = gson.fromJson(jsonVoterListResponce, VoterListDtoResponse.class);
        List<Voter> list1 = voterListDtoResponse.getVoterList();
        assertEquals(10, list1.size());

        String proposalTitle11 = "Прекратить жестокое обращение с летучими мышами на рок-фестивалях";
        String proposalTitle12 = "Оградить детей от пагубного влияния алкоголя и табакокурения";
        String proposalTitle21 = "Мир всем!";
        String proposalTitle31 = "Переснять Терминатор-2";
        String proposalTitle32 = "Пятилетке качества - рабочую гарантию";
        String proposalTitle41 = "Повысить зарплату деятелям культуры";
        String proposalTitle42 = "Сократить время труда и увеличить время отдыха";
        String proposalTitle43 = "Материнский капитал за первого ребенка!";
        String proposalTitle51 = "Ввести комендантский час";
        String proposalTitle81 = "Снова сделать А1 - первым альтернативным";
        String proposalTitle82 = "Свободу Анджеле Дэвис!";
        String proposalTitle101 = "Вор должен сидеть в тюрьме";

        AddProposalDtoRequest addProposalDtoRequest11 = new AddProposalDtoRequest(response1.getToken(), proposalTitle11);
        AddProposalDtoRequest addProposalDtoRequest12 = new AddProposalDtoRequest(response1.getToken(), proposalTitle12);
        AddProposalDtoRequest addProposalDtoRequest21 = new AddProposalDtoRequest(response2.getToken(), proposalTitle21);
        AddProposalDtoRequest addProposalDtoRequest31 = new AddProposalDtoRequest(response3.getToken(), proposalTitle31);
        AddProposalDtoRequest addProposalDtoRequest32 = new AddProposalDtoRequest(response3.getToken(), proposalTitle32);
        AddProposalDtoRequest addProposalDtoRequest41 = new AddProposalDtoRequest(response4.getToken(), proposalTitle41);
        AddProposalDtoRequest addProposalDtoRequest42 = new AddProposalDtoRequest(response4.getToken(), proposalTitle42);
        AddProposalDtoRequest addProposalDtoRequest43 = new AddProposalDtoRequest(response4.getToken(), proposalTitle43);
        AddProposalDtoRequest addProposalDtoRequest51 = new AddProposalDtoRequest(response5.getToken(), proposalTitle51);
        AddProposalDtoRequest addProposalDtoRequest81 = new AddProposalDtoRequest(response8.getToken(), proposalTitle81);
        AddProposalDtoRequest addProposalDtoRequest82 = new AddProposalDtoRequest(response8.getToken(), proposalTitle82);
        AddProposalDtoRequest addProposalDtoRequest101 = new AddProposalDtoRequest(response10.getToken(), proposalTitle101);

        String jsonAddProposalRequest11 = gson.toJson(addProposalDtoRequest11);
        String jsonAddProposalResponse11 = server.addProposal(jsonAddProposalRequest11);
        String jsonAddProposalRequest12 = gson.toJson(addProposalDtoRequest12);
        String jsonAddProposalResponse12 = server.addProposal(jsonAddProposalRequest12);
        String jsonAddProposalRequest21 = gson.toJson(addProposalDtoRequest21);
        String jsonAddProposalResponse21 = server.addProposal(jsonAddProposalRequest21);
        String jsonAddProposalRequest31 = gson.toJson(addProposalDtoRequest31);
        String jsonAddProposalResponse31 = server.addProposal(jsonAddProposalRequest31);
        String jsonAddProposalRequest32 = gson.toJson(addProposalDtoRequest32);
        String jsonAddProposalResponse32 = server.addProposal(jsonAddProposalRequest32);
        String jsonAddProposalRequest41 = gson.toJson(addProposalDtoRequest41);
        String jsonAddProposalResponse41 = server.addProposal(jsonAddProposalRequest41);
        String jsonAddProposalRequest42 = gson.toJson(addProposalDtoRequest42);
        String jsonAddProposalResponse42 = server.addProposal(jsonAddProposalRequest42);
        String jsonAddProposalRequest43 = gson.toJson(addProposalDtoRequest43);
        String jsonAddProposalResponse43 = server.addProposal(jsonAddProposalRequest43);
        String jsonAddProposalRequest51 = gson.toJson(addProposalDtoRequest51);
        String jsonAddProposalResponse51 = server.addProposal(jsonAddProposalRequest51);
        String jsonAddProposalRequest81 = gson.toJson(addProposalDtoRequest81);
        String jsonAddProposalResponse81 = server.addProposal(jsonAddProposalRequest81);
        String jsonAddProposalRequest82 = gson.toJson(addProposalDtoRequest82);
        String jsonAddProposalResponse82 = server.addProposal(jsonAddProposalRequest82);
        String jsonAddProposalRequest101 = gson.toJson(addProposalDtoRequest101);
        String jsonAddProposalResponse101 = server.addProposal(jsonAddProposalRequest101);

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest41 = new ProposalListWithRatingDtoRequest(response4.getToken());
        String jsonProposalListWithRatingDtoRequest41 = gson.toJson(proposalListWithRatingDtoRequest41);
        String jsonProposalListWithRatingDtoResponse41 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest41);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse41 = gson.fromJson(jsonProposalListWithRatingDtoResponse41, ProposalListWithRatingDtoResponse.class);

        int size1 = proposalListWithRatingDtoResponse41.getProposalListWithRating().size();
        assertEquals(12, size1);
        int index1 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse41.getProposalListWithRating(), proposalTitle31);

        AddProposalRatingDtoRequest addProposalRatingDtoRequest41 = new AddProposalRatingDtoRequest(response4.getToken(), proposalListWithRatingDtoResponse41.getProposalListWithRating().get(index1).getProposalTitle(), 4);
        String jsonAddProposalRatingDtoRequest41 = gson.toJson(addProposalRatingDtoRequest41);
        String jsonAddProposalRatingDtoResponse41 = server.addProposalRating(jsonAddProposalRatingDtoRequest41);
        AddProposalRatingDtoResponse addProposalRatingDtoResponse41 = gson.fromJson(jsonAddProposalRatingDtoResponse41, AddProposalRatingDtoResponse.class);
        assertEquals("ok", addProposalRatingDtoResponse41.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest61 = new ProposalListWithRatingDtoRequest(response6.getToken());
        String jsonProposalListWithRatingDtoRequest61 = gson.toJson(proposalListWithRatingDtoRequest61);
        String jsonProposalListWithRatingDtoResponse61 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest61);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse61 = gson.fromJson(jsonProposalListWithRatingDtoResponse61, ProposalListWithRatingDtoResponse.class);

        int index2 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse61.getProposalListWithRating(), proposalTitle82);

        AddProposalRatingDtoRequest addProposalRatingDtoRequest61 = new AddProposalRatingDtoRequest(response6.getToken(), proposalListWithRatingDtoResponse61.getProposalListWithRating().get(index2).getProposalTitle(), 2);
        String jsonAddProposalRatingDtoRequest61 = gson.toJson(addProposalRatingDtoRequest61);
        String jsonAddProposalRatingDtoResponse61 = server.addProposalRating(jsonAddProposalRatingDtoRequest61);
        AddProposalRatingDtoResponse addProposalRatingDtoResponse61 = gson.fromJson(jsonAddProposalRatingDtoResponse61, AddProposalRatingDtoResponse.class);
        assertEquals("ok", addProposalRatingDtoResponse61.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest62 = new ProposalListWithRatingDtoRequest(response6.getToken());
        String jsonProposalListWithRatingDtoRequest62 = gson.toJson(proposalListWithRatingDtoRequest62);
        String jsonProposalListWithRatingDtoResponse62 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest62);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse62 = gson.fromJson(jsonProposalListWithRatingDtoResponse62, ProposalListWithRatingDtoResponse.class);

        int index3 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse62.getProposalListWithRating(), proposalTitle21);

        AddProposalRatingDtoRequest addProposalRatingDtoRequest62 = new AddProposalRatingDtoRequest(response6.getToken(), proposalListWithRatingDtoResponse62.getProposalListWithRating().get(index3).getProposalTitle(), 3);
        String jsonAddProposalRatingDtoRequest62 = gson.toJson(addProposalRatingDtoRequest62);
        String jsonAddProposalRatingDtoResponse62 = server.addProposalRating(jsonAddProposalRatingDtoRequest62);
        AddProposalRatingDtoResponse addProposalRatingDtoResponse62 = gson.fromJson(jsonAddProposalRatingDtoResponse62, AddProposalRatingDtoResponse.class);
        assertEquals("ok", addProposalRatingDtoResponse62.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest71 = new ProposalListWithRatingDtoRequest(response7.getToken());
        String jsonProposalListWithRatingDtoRequest71 = gson.toJson(proposalListWithRatingDtoRequest71);
        String jsonProposalListWithRatingDtoResponse71 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest71);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse71 = gson.fromJson(jsonProposalListWithRatingDtoResponse71, ProposalListWithRatingDtoResponse.class);

        int index4 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse71.getProposalListWithRating(), proposalTitle31);

        AddProposalRatingDtoRequest addProposalRatingDtoRequest71 = new AddProposalRatingDtoRequest(response7.getToken(), proposalListWithRatingDtoResponse71.getProposalListWithRating().get(index4).getProposalTitle(), 4);
        String jsonAddProposalRatingDtoRequest71 = gson.toJson(addProposalRatingDtoRequest71);
        String jsonAddProposalRatingDtoResponse71 = server.addProposalRating(jsonAddProposalRatingDtoRequest71);
        AddProposalRatingDtoResponse addProposalRatingDtoResponse71 = gson.fromJson(jsonAddProposalRatingDtoResponse71, AddProposalRatingDtoResponse.class);
        assertEquals("ok", addProposalRatingDtoResponse71.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest81 = new ProposalListWithRatingDtoRequest(response8.getToken());
        String jsonProposalListWithRatingDtoRequest81 = gson.toJson(proposalListWithRatingDtoRequest81);
        String jsonProposalListWithRatingDtoResponse81 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest81);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse81 = gson.fromJson(jsonProposalListWithRatingDtoResponse81, ProposalListWithRatingDtoResponse.class);

        int index5 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse81.getProposalListWithRating(), proposalTitle101);

        AddProposalRatingDtoRequest addProposalRatingDtoRequest81 = new AddProposalRatingDtoRequest(response8.getToken(), proposalListWithRatingDtoResponse81.getProposalListWithRating().get(index5).getProposalTitle(), 3);
        String jsonAddProposalRatingDtoRequest81 = gson.toJson(addProposalRatingDtoRequest81);
        String jsonAddProposalRatingDtoResponse81 = server.addProposalRating(jsonAddProposalRatingDtoRequest81);
        AddProposalRatingDtoResponse addProposalRatingDtoResponse81 = gson.fromJson(jsonAddProposalRatingDtoResponse81, AddProposalRatingDtoResponse.class);
        assertEquals("ok", addProposalRatingDtoResponse81.getStatus());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest42 = new ProposalListWithRatingDtoRequest(response4.getToken());
        String jsonProposalListWithRatingDtoRequest42 = gson.toJson(proposalListWithRatingDtoRequest42);
        String jsonProposalListWithRatingDtoResponse42 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest42);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse42 = gson.fromJson(jsonProposalListWithRatingDtoResponse42, ProposalListWithRatingDtoResponse.class);
        int size2 = proposalListWithRatingDtoResponse42.getProposalListWithRating().size();
        assertEquals(3.5, proposalListWithRatingDtoResponse42.getProposalListWithRating().get(size2 - 1).getAvgRating(), 0);

        int index6 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse42.getProposalListWithRating(), proposalTitle31);

        RemoveProposalRatingDtoRequest removeProposalRatingDtoRequest4 = new RemoveProposalRatingDtoRequest(response4.getToken(), proposalListWithRatingDtoResponse42.getProposalListWithRating().get(index6).getProposalTitle());
        String jsonRemoveProposalRatingDtoRequest4 = gson.toJson(removeProposalRatingDtoRequest4);
        String jsonRemoveProposalRatingDtoResponse4 = server.removeProposalRating(jsonRemoveProposalRatingDtoRequest4);
        RemoveProposalRatingDtoResponse removeProposalRatingDtoResponse4 = gson.fromJson(jsonRemoveProposalRatingDtoResponse4, RemoveProposalRatingDtoResponse.class);
        assertEquals("ok", removeProposalRatingDtoResponse4.getStatus());

        LogoutVoterDtoRequest logoutVoterDtoRequest2 = new LogoutVoterDtoRequest(response2.getToken());
        String jsonLogoutVoterDtoRequest2 = gson.toJson(logoutVoterDtoRequest2);
        String jsonLogoutVoterDtoResponse2 = server.logout(jsonLogoutVoterDtoRequest2);
        LogoutVoterDtoResponse logoutVoterDtoResponse2 = gson.fromJson(jsonLogoutVoterDtoResponse2, LogoutVoterDtoResponse.class);
        assertNull(logoutVoterDtoResponse2.getToken());

        LogoutVoterDtoRequest logoutVoterDtoRequest8 = new LogoutVoterDtoRequest(response8.getToken());
        String jsonLogoutVoterDtoRequest8 = gson.toJson(logoutVoterDtoRequest8);
        String jsonLogoutVoterDtoResponse8 = server.logout(jsonLogoutVoterDtoRequest8);
        LogoutVoterDtoResponse logoutVoterDtoResponse8 = gson.fromJson(jsonLogoutVoterDtoResponse8, LogoutVoterDtoResponse.class);
        assertNull(logoutVoterDtoResponse8.getToken());

        VoterListDtoRequest voterListDtoRequest11 = new VoterListDtoRequest(response1.getToken());
        String jsonVoterListRequest11 = gson.toJson(voterListDtoRequest11);
        String jsonVoterListResponce11 = server.getVoterList(jsonVoterListRequest11);
        VoterListDtoResponse voterListDtoResponse11 = gson.fromJson(jsonVoterListResponce11, VoterListDtoResponse.class);

        List<Voter> list2 = voterListDtoResponse.getVoterList();
        sortVoterList(list2);

        AddCandidateDtoRequest addCandidateDtoRequest11 = new AddCandidateDtoRequest(response1.getToken(), list2.get(0).getId());
        String jsonAddCandidateRequest11 = gson.toJson(addCandidateDtoRequest11);
        String jsonAddCandidateResponce11 = server.addCandidate(jsonAddCandidateRequest11);
        AddCandidateDtoResponse addCandidateDtoResponse11 = gson.fromJson(jsonAddCandidateResponce11, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse11.getStatus());

        VoterListDtoRequest voterListDtoRequest31 = new VoterListDtoRequest(response3.getToken());
        String jsonVoterListRequest31 = gson.toJson(voterListDtoRequest31);
        String jsonVoterListResponce31 = server.getVoterList(jsonVoterListRequest31);
        VoterListDtoResponse voterListDtoResponse31 = gson.fromJson(jsonVoterListResponce31, VoterListDtoResponse.class);

        List<Voter> list3 = voterListDtoResponse.getVoterList();
        sortVoterList(list3);

        AddCandidateDtoRequest addCandidateDtoRequest31 = new AddCandidateDtoRequest(response3.getToken(), list3.get(3).getId());
        String jsonAddCandidateRequest31 = gson.toJson(addCandidateDtoRequest31);
        String jsonAddCandidateResponce31 = server.addCandidate(jsonAddCandidateRequest31);
        AddCandidateDtoResponse addCandidateDtoResponse31 = gson.fromJson(jsonAddCandidateResponce31, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse31.getStatus());

        VoterListDtoRequest voterListDtoRequest91 = new VoterListDtoRequest(response9.getToken());
        String jsonVoterListRequest91 = gson.toJson(voterListDtoRequest91);
        String jsonVoterListResponce91 = server.getVoterList(jsonVoterListRequest91);
        VoterListDtoResponse voterListDtoResponse91 = gson.fromJson(jsonVoterListResponce91, VoterListDtoResponse.class);

        List<Voter> list4 = voterListDtoResponse.getVoterList();
        sortVoterList(list4);

        AddCandidateDtoRequest addCandidateDtoRequest91 = new AddCandidateDtoRequest(response9.getToken(), list4.get(5).getId());
        String jsonAddCandidateRequest91 = gson.toJson(addCandidateDtoRequest91);
        String jsonAddCandidateResponce91 = server.addCandidate(jsonAddCandidateRequest91);
        AddCandidateDtoResponse addCandidateDtoResponse91 = gson.fromJson(jsonAddCandidateResponce91, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse91.getStatus());

        LoginVoterDtoRequest loginVoterDtoRequest2 = new LoginVoterDtoRequest("PrinceOfDarknessToo", "Trash");
        String jsonLoginVoterDtoRequest2 = gson.toJson(loginVoterDtoRequest2);
        String jsonLoginVoterDtoResponse2 = server.login(jsonLoginVoterDtoRequest2);
        LoginVoterDtoResponse loginVoterDtoResponse2 = gson.fromJson(jsonLoginVoterDtoResponse2, LoginVoterDtoResponse.class);
        assertNotNull(loginVoterDtoResponse2.getToken());

        CandidateAgreeDtoRequest candidateAgreeDtoRequest41 = new CandidateAgreeDtoRequest(response4.getToken(), true);
        String jsonCandidateAgreeDtoRequest41 = gson.toJson(candidateAgreeDtoRequest41);
        String jsonCandidateAgreeDtoResponce41 = server.agree(jsonCandidateAgreeDtoRequest41);
        CandidateAgreeDtoResponse candidateAgreeDtoResponse41 = gson.fromJson(jsonCandidateAgreeDtoResponce41, CandidateAgreeDtoResponse.class);
        assertEquals("ok", candidateAgreeDtoResponse41.getStatus());

        CandidateAgreeDtoRequest candidateAgreeDtoRequest61 = new CandidateAgreeDtoRequest(response6.getToken(), false);
        String jsonCandidateAgreeDtoRequest61 = gson.toJson(candidateAgreeDtoRequest61);
        String jsonCandidateAgreeDtoResponce61 = server.agree(jsonCandidateAgreeDtoRequest61);
        CandidateAgreeDtoResponse candidateAgreeDtoResponse61 = gson.fromJson(jsonCandidateAgreeDtoResponce61, CandidateAgreeDtoResponse.class);
        assertEquals("ok", candidateAgreeDtoResponse61.getStatus());

        VoterListDtoRequest voterListDtoRequest71 = new VoterListDtoRequest(response7.getToken());
        String jsonVoterListRequest71 = gson.toJson(voterListDtoRequest71);
        String jsonVoterListResponce71 = server.getVoterList(jsonVoterListRequest71);
        VoterListDtoResponse voterListDtoResponse71 = gson.fromJson(jsonVoterListResponce71, VoterListDtoResponse.class);

        List<Voter> list5 = voterListDtoResponse.getVoterList();
        sortVoterList(list5);

        AddCandidateDtoRequest addCandidateDtoRequest71 = new AddCandidateDtoRequest(response7.getToken(), list5.get(6).getId());
        String jsonAddCandidateRequest71 = gson.toJson(addCandidateDtoRequest71);
        String jsonAddCandidateResponce71 = server.addCandidate(jsonAddCandidateRequest71);
        AddCandidateDtoResponse addCandidateDtoResponse71 = gson.fromJson(jsonAddCandidateResponce71, AddCandidateDtoResponse.class);
        assertEquals("ok", addCandidateDtoResponse71.getStatus());

        LogoutVoterDtoRequest logoutVoterDtoRequest4 = new LogoutVoterDtoRequest(response4.getToken());
        String jsonLogoutVoterDtoRequest4 = gson.toJson(logoutVoterDtoRequest4);
        String jsonLogoutVoterDtoResponse4 = server.logout(jsonLogoutVoterDtoRequest4);
        LogoutVoterDtoResponse logoutVoterDtoResponse4 = gson.fromJson(jsonLogoutVoterDtoResponse4, LogoutVoterDtoResponse.class);
        assertNull(logoutVoterDtoResponse4.getToken());

        GiveVoteDtoRequest giveEarlyVoteDtoRequest = new GiveVoteDtoRequest(response6.getToken());
        String jsonGiveEarlyVoteDtoRequest = gson.toJson(giveEarlyVoteDtoRequest);
        String jsonGiveEarlyVoteDtoResponse = server.giveVote(jsonGiveEarlyVoteDtoRequest);
        ElectionErrors giveEarlyVoteDtoResponse = gson.fromJson(jsonGiveEarlyVoteDtoResponse, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_NOT_STARTED_OR_FINISHED.getErrorString(), giveEarlyVoteDtoResponse.getError());

        GetMayorDtoRequest getEarlyMayorDtoRequest1 = new GetMayorDtoRequest(response1.getToken());
        String jsonGetEarlyMayorDtoRequest1 = gson.toJson(getEarlyMayorDtoRequest1);
        String jsonGetEarlyMayorDtoResponse1 = server.getMayor(jsonGetEarlyMayorDtoRequest1);
        ElectionErrors getEarlyMayorDtoResponse1 = gson.fromJson(jsonGetEarlyMayorDtoResponse1, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_NOT_FINISHED.getErrorString(), getEarlyMayorDtoResponse1.getError());

        server.startElection();

        AddProposalDtoRequest addProposalDtoRequest92 = new AddProposalDtoRequest(response9.getToken(), "Вымостить все дорожки брусчаткой");
        String jsonAddProposalRequest92 = gson.toJson(addProposalDtoRequest92);
        String jsonAddProposalResponse92 = server.addProposal(jsonAddProposalRequest92);
        ElectionErrors addProposalDtoResponse92 = gson.fromJson(jsonAddProposalResponse92, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), addProposalDtoResponse92.getError());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest101 = new ProposalListWithRatingDtoRequest(response10.getToken());
        String jsonProposalListWithRatingDtoRequest101 = gson.toJson(proposalListWithRatingDtoRequest101);
        String jsonProposalListWithRatingDtoResponse101 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest101);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse101 = gson.fromJson(jsonProposalListWithRatingDtoResponse101, ProposalListWithRatingDtoResponse.class);

        int index7 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse101.getProposalListWithRating(), proposalTitle21);

        AddProposalRatingDtoRequest addProposalRatingDtoRequest101 = new AddProposalRatingDtoRequest(response10.getToken(), proposalListWithRatingDtoResponse101.getProposalListWithRating().get(index7).getProposalTitle(), 3);
        String jsonAddProposalRatingDtoRequest101 = gson.toJson(addProposalRatingDtoRequest101);
        String jsonAddProposalRatingDtoResponse101 = server.addProposalRating(jsonAddProposalRatingDtoRequest101);
        ElectionErrors addProposalRatingDtoResponse101 = gson.fromJson(jsonAddProposalRatingDtoResponse101, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), addProposalRatingDtoResponse101.getError());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest63 = new ProposalListWithRatingDtoRequest(response6.getToken());
        String jsonProposalListWithRatingDtoRequest63 = gson.toJson(proposalListWithRatingDtoRequest63);
        String jsonProposalListWithRatingDtoResponse63 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest63);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse63 = gson.fromJson(jsonProposalListWithRatingDtoResponse63, ProposalListWithRatingDtoResponse.class);

        int index8 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse63.getProposalListWithRating(), proposalTitle12);

        RemoveProposalRatingDtoRequest removeProposalRatingDtoRequest63 = new RemoveProposalRatingDtoRequest(response6.getToken(), proposalListWithRatingDtoResponse63.getProposalListWithRating().get(index8).getProposalTitle());
        String jsonRemoveProposalRatingDtoRequest63 = gson.toJson(removeProposalRatingDtoRequest63);
        String jsonRemoveProposalRatingDtoResponse63 = server.removeProposalRating(jsonRemoveProposalRatingDtoRequest63);
        ElectionErrors removeProposalRatingDtoResponse63 = gson.fromJson(jsonRemoveProposalRatingDtoResponse63, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), removeProposalRatingDtoResponse63.getError());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest11 = new ProposalListWithRatingDtoRequest(response1.getToken());
        String jsonProposalListWithRatingDtoRequest11 = gson.toJson(proposalListWithRatingDtoRequest11);
        String jsonProposalListWithRatingDtoResponse11 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest11);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse11 = gson.fromJson(jsonProposalListWithRatingDtoResponse11, ProposalListWithRatingDtoResponse.class);

        int index9 = getProposalAvgRatingIndex(proposalListWithRatingDtoResponse11.getProposalListWithRating(), proposalTitle31);

        AddProposalToProgrammeDtoRequest addProposalToProgrammeDtoRequest11 = new AddProposalToProgrammeDtoRequest(response1.getToken(), proposalListWithRatingDtoResponse11.getProposalListWithRating().get(index9).getProposalTitle());
        String jsonAddProposalToProgrammeDtoRequest11 = gson.toJson(addProposalToProgrammeDtoRequest11);
        String jsonAddProposalToProgrammeDtoResponse11 = server.addProposalToProgramme(jsonAddProposalToProgrammeDtoRequest11);
        ElectionErrors addProposalToProgrammeDtoResponse11 = gson.fromJson(jsonAddProposalToProgrammeDtoResponse11, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), addProposalToProgrammeDtoResponse11.getError());

        OwnProgrammeDtoRequest ownProgrammeDtoRequest11 = new OwnProgrammeDtoRequest(response1.getToken());
        String jsonOwnProgrammeDtoRequest11 = gson.toJson(ownProgrammeDtoRequest11);
        String jsonOwnProgrammeDtoResponse11 = server.getOwnProgramme(jsonOwnProgrammeDtoRequest11);
        OwnProgrammeDtoResponse ownProgrammeDtoResponse11 = gson.fromJson(jsonOwnProgrammeDtoResponse11, OwnProgrammeDtoResponse.class);

        RemoveProposalFromProgrammeDtoRequest removeProposalFromProgrammeDtoRequest11 = new RemoveProposalFromProgrammeDtoRequest(response1.getToken(), ownProgrammeDtoResponse11.getProgramme().get(1).getProposalTitle());
        String jsonRemoveProposalFromProgrammeDtoRequest11 = gson.toJson(removeProposalFromProgrammeDtoRequest11);
        String jsonRemoveProposalFromProgrammeDtoResponse11 = server.removeProposalFromProgramme(jsonRemoveProposalFromProgrammeDtoRequest11);
        ElectionErrors removeProposalFromProgrammeDtoResponse11 = gson.fromJson(jsonRemoveProposalFromProgrammeDtoResponse11, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), removeProposalFromProgrammeDtoResponse11.getError());

        String jsonRequest11 = gson.toJson(request11);
        String jsonResponce11 = server.registerVoter(jsonRequest11);
        ElectionErrors response11 = gson.fromJson(jsonResponce11, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), response11.getError());

        RemoveCandidateDtoRequest removeCandidateDtoRequest71 = new RemoveCandidateDtoRequest(response7.getToken());
        String jsonRemoveCandidateDtoRequest71 = gson.toJson(removeCandidateDtoRequest71);
        String jsonRemoveCandidateDtoResponce71 = server.removeCandidate(jsonRemoveCandidateDtoRequest71);
        ElectionErrors removeCandidateDtoResponse71 = gson.fromJson(jsonRemoveCandidateDtoResponce71, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), removeCandidateDtoResponse71.getError());

        CandidateListDtoRequest candidateListDtoRequest11 = new CandidateListDtoRequest(response1.getToken());
        String jsonCandidateListDtoRequest11 = gson.toJson(candidateListDtoRequest11);
        String jsonCandidateListDtoResponse11 = server.getCandidateList(jsonCandidateListDtoRequest11);
        CandidateListDtoResponse candidateListDtoResponse11 = gson.fromJson(jsonCandidateListDtoResponse11, CandidateListDtoResponse.class);
        assertEquals(2, candidateListDtoResponse11.getCandidateList().size());

        List<CandidateList> candidateList1 = candidateListDtoResponse11.getCandidateList();
        sortCandidateList(candidateList1);

        GiveVoteDtoRequest giveVoteDtoRequest2 = new GiveVoteDtoRequest(loginVoterDtoResponse2.getToken(), candidateList1.get(0).getId());
        String jsonGiveVoteDtoRequest2 = gson.toJson(giveVoteDtoRequest2);
        String jsonGiveVoteDtoResponse2 = server.giveVote(jsonGiveVoteDtoRequest2);
        GiveVoteDtoResponse giveVoteDtoResponse2 = gson.fromJson(jsonGiveVoteDtoResponse2, GiveVoteDtoResponse.class);
        assertEquals("ok", giveVoteDtoResponse2.getStatus());

        GiveVoteDtoRequest giveVoteDtoRequest3 = new GiveVoteDtoRequest(response3.getToken(), candidateList1.get(1).getId());
        String jsonGiveVoteDtoRequest3 = gson.toJson(giveVoteDtoRequest3);
        String jsonGiveVoteDtoResponse3 = server.giveVote(jsonGiveVoteDtoRequest3);
        GiveVoteDtoResponse giveVoteDtoResponse3 = gson.fromJson(jsonGiveVoteDtoResponse3, GiveVoteDtoResponse.class);
        assertEquals("ok", giveVoteDtoResponse3.getStatus());

        GiveVoteDtoRequest giveVoteDtoRequest4 = new GiveVoteDtoRequest(logoutVoterDtoResponse4.getToken(), candidateList1.get(0).getId());
        String jsonGiveVoteDtoRequest4 = gson.toJson(giveVoteDtoRequest4);
        String jsonGiveVoteDtoResponse4 = server.giveVote(jsonGiveVoteDtoRequest4);
        ElectionErrors giveVoteDtoResponse4 = gson.fromJson(jsonGiveVoteDtoResponse4, ElectionErrors.class);
        assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), giveVoteDtoResponse4.getError());

        GiveVoteDtoRequest giveVoteDtoRequest5 = new GiveVoteDtoRequest(response5.getToken(), candidateList1.get(0).getId());
        String jsonGiveVoteDtoRequest5 = gson.toJson(giveVoteDtoRequest5);
        String jsonGiveVoteDtoResponse5 = server.giveVote(jsonGiveVoteDtoRequest5);
        GiveVoteDtoResponse giveVoteDtoResponse5 = gson.fromJson(jsonGiveVoteDtoResponse5, GiveVoteDtoResponse.class);
        assertEquals("ok", giveVoteDtoResponse5.getStatus());

        GiveVoteDtoRequest giveVoteDtoRequest6 = new GiveVoteDtoRequest(response6.getToken());
        String jsonGiveVoteDtoRequest6 = gson.toJson(giveVoteDtoRequest6);
        String jsonGiveVoteDtoResponse6 = server.giveVote(jsonGiveVoteDtoRequest6);
        GiveVoteDtoResponse giveVoteDtoResponse6 = gson.fromJson(jsonGiveVoteDtoResponse6, GiveVoteDtoResponse.class);
        assertEquals("ok", giveVoteDtoResponse6.getStatus());

        GiveVoteDtoRequest giveVoteDtoRequest8 = new GiveVoteDtoRequest(logoutVoterDtoResponse8.getToken(), candidateList1.get(0).getId());
        String jsonGiveVoteDtoRequest8 = gson.toJson(giveVoteDtoRequest8);
        String jsonGiveVoteDtoResponse8 = server.giveVote(jsonGiveVoteDtoRequest8);
        ElectionErrors giveVoteDtoResponse8 = gson.fromJson(jsonGiveVoteDtoResponse8, ElectionErrors.class);
        assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), giveVoteDtoResponse8.getError());

        GiveVoteDtoRequest giveVoteDtoRequest9 = new GiveVoteDtoRequest(response9.getToken(), candidateList1.get(0).getId());
        String jsonGiveVoteDtoRequest9 = gson.toJson(giveVoteDtoRequest9);
        String jsonGiveVoteDtoResponse9 = server.giveVote(jsonGiveVoteDtoRequest9);
        GiveVoteDtoResponse giveVoteDtoResponse9 = gson.fromJson(jsonGiveVoteDtoResponse9, GiveVoteDtoResponse.class);
        assertEquals("ok", giveVoteDtoResponse9.getStatus());

        GiveVoteDtoRequest giveVoteDtoRequest10 = new GiveVoteDtoRequest(response10.getToken());
        String jsonGiveVoteDtoRequest10 = gson.toJson(giveVoteDtoRequest10);
        String jsonGiveVoteDtoResponse10 = server.giveVote(jsonGiveVoteDtoRequest10);
        GiveVoteDtoResponse giveVoteDtoResponse10 = gson.fromJson(jsonGiveVoteDtoResponse10, GiveVoteDtoResponse.class);
        assertEquals("ok", giveVoteDtoResponse10.getStatus());

        GetMayorDtoRequest getEarlyMayorDtoRequest2 = new GetMayorDtoRequest(response1.getToken());
        String jsonGetEarlyMayorDtoRequest2 = gson.toJson(getEarlyMayorDtoRequest2);
        String jsonGetEarlyMayorDtoResponse2 = server.getMayor(jsonGetEarlyMayorDtoRequest2);
        ElectionErrors getEarlyMayorDtoResponse2 = gson.fromJson(jsonGetEarlyMayorDtoResponse2, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_NOT_FINISHED.getErrorString(), getEarlyMayorDtoResponse2.getError());

        server.stopElection();

        String jsonRequest12 = gson.toJson(request11);
        String jsonResponce12 = server.registerVoter(jsonRequest12);
        ElectionErrors response12 = gson.fromJson(jsonResponce11, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), response12.getError());

        VoterListDtoRequest voterListDtoRequest32 = new VoterListDtoRequest(response3.getToken());
        String jsonVoterListRequest32 = gson.toJson(voterListDtoRequest32);
        String jsonVoterListResponce32 = server.getVoterList(jsonVoterListRequest32);
        VoterListDtoResponse voterListDtoResponse32 = gson.fromJson(jsonVoterListResponce32, VoterListDtoResponse.class);

        AddCandidateDtoRequest addCandidateDtoRequest32 = new AddCandidateDtoRequest(response7.getToken(), 6);
        String jsonAddCandidateRequest32 = gson.toJson(addCandidateDtoRequest32);
        String jsonAddCandidateResponce32 = server.addCandidate(jsonAddCandidateRequest32);
        ElectionErrors addCandidateDtoResponse32 = gson.fromJson(jsonAddCandidateResponce32, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), addCandidateDtoResponse32.getError());

        RemoveCandidateDtoRequest removeCandidateDtoRequest72 = new RemoveCandidateDtoRequest(response7.getToken());
        String jsonRemoveCandidateDtoRequest72 = gson.toJson(removeCandidateDtoRequest72);
        String jsonRemoveCandidateDtoResponce72 = server.removeCandidate(jsonRemoveCandidateDtoRequest72);
        ElectionErrors removeCandidateDtoResponse72 = gson.fromJson(jsonRemoveCandidateDtoResponce72, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), removeCandidateDtoResponse71.getError());

        AddProposalDtoRequest addProposalDtoRequest93 = new AddProposalDtoRequest(response9.getToken(), "Вымостить все дорожки брусчаткой");
        String jsonAddProposalRequest93 = gson.toJson(addProposalDtoRequest93);
        String jsonAddProposalResponse93 = server.addProposal(jsonAddProposalRequest93);
        ElectionErrors addProposalDtoResponse93 = gson.fromJson(jsonAddProposalResponse93, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), addProposalDtoResponse93.getError());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest102 = new ProposalListWithRatingDtoRequest(response10.getToken());
        String jsonProposalListWithRatingDtoRequest102 = gson.toJson(proposalListWithRatingDtoRequest102);
        String jsonProposalListWithRatingDtoResponse102 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest102);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse102 = gson.fromJson(jsonProposalListWithRatingDtoResponse102, ProposalListWithRatingDtoResponse.class);
        AddProposalRatingDtoRequest addProposalRatingDtoRequest102 = new AddProposalRatingDtoRequest(response10.getToken(), proposalListWithRatingDtoResponse102.getProposalListWithRating().get(2).getProposalTitle(), 3);
        String jsonAddProposalRatingDtoRequest102 = gson.toJson(addProposalRatingDtoRequest102);
        String jsonAddProposalRatingDtoResponse102 = server.addProposalRating(jsonAddProposalRatingDtoRequest102);
        ElectionErrors addProposalRatingDtoResponse102 = gson.fromJson(jsonAddProposalRatingDtoResponse102, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), addProposalRatingDtoResponse102.getError());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest64 = new ProposalListWithRatingDtoRequest(response6.getToken());
        String jsonProposalListWithRatingDtoRequest64 = gson.toJson(proposalListWithRatingDtoRequest64);
        String jsonProposalListWithRatingDtoResponse64 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest64);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse64 = gson.fromJson(jsonProposalListWithRatingDtoResponse64, ProposalListWithRatingDtoResponse.class);
        RemoveProposalRatingDtoRequest removeProposalRatingDtoRequest64 = new RemoveProposalRatingDtoRequest(response6.getToken(), proposalListWithRatingDtoResponse63.getProposalListWithRating().get(1).getProposalTitle());
        String jsonRemoveProposalRatingDtoRequest64 = gson.toJson(removeProposalRatingDtoRequest64);
        String jsonRemoveProposalRatingDtoResponse64 = server.removeProposalRating(jsonRemoveProposalRatingDtoRequest64);
        ElectionErrors removeProposalRatingDtoResponse64 = gson.fromJson(jsonRemoveProposalRatingDtoResponse64, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), removeProposalRatingDtoResponse64.getError());

        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest12 = new ProposalListWithRatingDtoRequest(response1.getToken());
        String jsonProposalListWithRatingDtoRequest12 = gson.toJson(proposalListWithRatingDtoRequest12);
        String jsonProposalListWithRatingDtoResponse12 = server.getProposalListWithRating(jsonProposalListWithRatingDtoRequest12);
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse12 = gson.fromJson(jsonProposalListWithRatingDtoResponse12, ProposalListWithRatingDtoResponse.class);
        AddProposalToProgrammeDtoRequest addProposalToProgrammeDtoRequest12 = new AddProposalToProgrammeDtoRequest(response1.getToken(), proposalListWithRatingDtoResponse11.getProposalListWithRating().get(3).getProposalTitle());
        String jsonAddProposalToProgrammeDtoRequest12 = gson.toJson(addProposalToProgrammeDtoRequest12);
        String jsonAddProposalToProgrammeDtoResponse12 = server.addProposalToProgramme(jsonAddProposalToProgrammeDtoRequest12);
        ElectionErrors addProposalToProgrammeDtoResponse12 = gson.fromJson(jsonAddProposalToProgrammeDtoResponse12, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), addProposalToProgrammeDtoResponse12.getError());

        OwnProgrammeDtoRequest ownProgrammeDtoRequest12 = new OwnProgrammeDtoRequest(response1.getToken());
        String jsonOwnProgrammeDtoRequest12 = gson.toJson(ownProgrammeDtoRequest12);
        String jsonOwnProgrammeDtoResponse12 = server.getOwnProgramme(jsonOwnProgrammeDtoRequest12);
        OwnProgrammeDtoResponse ownProgrammeDtoResponse12 = gson.fromJson(jsonOwnProgrammeDtoResponse12, OwnProgrammeDtoResponse.class);
        RemoveProposalFromProgrammeDtoRequest removeProposalFromProgrammeDtoRequest12 = new RemoveProposalFromProgrammeDtoRequest(response1.getToken(), ownProgrammeDtoResponse11.getProgramme().get(1).getProposalTitle());
        String jsonRemoveProposalFromProgrammeDtoRequest12 = gson.toJson(removeProposalFromProgrammeDtoRequest12);
        String jsonRemoveProposalFromProgrammeDtoResponse12 = server.removeProposalFromProgramme(jsonRemoveProposalFromProgrammeDtoRequest12);
        ElectionErrors removeProposalFromProgrammeDtoResponse12 = gson.fromJson(jsonRemoveProposalFromProgrammeDtoResponse12, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED.getErrorString(), removeProposalFromProgrammeDtoResponse12.getError());

        GiveVoteDtoRequest giveLateVoteDtoRequest = new GiveVoteDtoRequest(response6.getToken());
        String jsonGiveLateVoteDtoRequest = gson.toJson(giveLateVoteDtoRequest);
        String jsonGiveLateVoteDtoResponse = server.giveVote(jsonGiveLateVoteDtoRequest);
        ElectionErrors giveLateVoteDtoResponse = gson.fromJson(jsonGiveLateVoteDtoResponse, ElectionErrors.class);
        assertEquals(ElectionErrorCode.ELECTIONS_NOT_STARTED_OR_FINISHED.getErrorString(), giveLateVoteDtoResponse.getError());

        GetMayorDtoRequest getMayorDtoRequest1 = new GetMayorDtoRequest(response1.getToken());
        String jsonGetMayorDtoRequest1 = gson.toJson(getMayorDtoRequest1);
        String jsonGetMayorDtoResponse1 = server.getMayor(jsonGetMayorDtoRequest1);
        GetMayorDtoResponse getMayorDtoResponse1 = gson.fromJson(jsonGetMayorDtoResponse1, GetMayorDtoResponse.class);
        Voter voter1 = new Voter(request1.getFirstName(), request1.getLastName(), request1.getPatronymic(), request1.getStreet(),
                request1.getHouse(), request1.getApartment(), request1.getLogin(), request1.getPassword());
        assertEquals(voter1.getFullName(), getMayorDtoResponse1.getMayor());
    }

    @Test
    public void testLoginLogoutValidation() {
        List<LoginVoterDtoRequest> requestList1 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList1 = new ArrayList<>();

        requestList1.add(new LoginVoterDtoRequest(null, "NiceGuy"));
        requestList1.add(new LoginVoterDtoRequest("", "NiceGuy"));
        requestList1.add(new LoginVoterDtoRequest(" PrinceOfDarkness", "NiceGuy"));
        requestList1.add(new LoginVoterDtoRequest("Prin", "NiceGuy"));
        requestList1.add(new LoginVoterDtoRequest("PrinceOfDarkness12345", "NiceGuy"));
        requestList1.add(new LoginVoterDtoRequest("PrinceOfDarkness", null));
        requestList1.add(new LoginVoterDtoRequest("PrinceOfDarkness", ""));
        requestList1.add(new LoginVoterDtoRequest("PrinceOfDarkness", "Nice Guy"));
        requestList1.add(new LoginVoterDtoRequest("PrinceOfDarkness", "niceguy"));
        requestList1.add(new LoginVoterDtoRequest("PrinceOfDarkness", "Nice"));
        requestList1.add(new LoginVoterDtoRequest("PrinceOfDarkness", "NiceGuyOzzyOsbornePrinceOfDarkness"));

        for (LoginVoterDtoRequest request : requestList1) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.login(jsonRequest);
            responseList1.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 4; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_LOGIN.getErrorString(), responseList1.get(i).getError());
        }
        for (int i = 5; i <= 9; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_PASSWORD.getErrorString(), responseList1.get(i).getError());
        }

        List<LogoutVoterDtoRequest> requestList2 = new ArrayList<>();
        List<ElectionErrorDtoResponse> responseList2 = new ArrayList<>();

        requestList2.add(new LogoutVoterDtoRequest(null));
        requestList2.add(new LogoutVoterDtoRequest("123as562"));
        requestList2.add(new LogoutVoterDtoRequest("9a1b2bd7-f76b-487f-bc35-52524bda82572"));

        for (LogoutVoterDtoRequest request : requestList2) {
            String jsonRequest = gson.toJson(request);
            String jsonResponse = server.logout(jsonRequest);
            responseList2.add(gson.fromJson(jsonResponse, ElectionErrorDtoResponse.class));
        }
        for (int i = 0; i <= 2; i++) {
            assertEquals(ElectionJsonParsingErrorCode.WRONG_TOKEN.getErrorString(), responseList2.get(i).getError());
        }
    }

    @Test
    public void testLoginLogoutWithError() {
        String jsonRequest1 = gson.toJson(request1);
        String jsonResponce1 = server.registerVoter(jsonRequest1);
        RegisterVoterDtoResponse response1 = gson.fromJson(jsonResponce1, RegisterVoterDtoResponse.class);
        assertNotNull(response1.getToken());

        LogoutVoterDtoRequest logoutRequest = new LogoutVoterDtoRequest(response1.getToken());
        String logoutJsonRequest = gson.toJson(logoutRequest);
        String jsonLogoutResponce = server.logout(logoutJsonRequest);
        LogoutVoterDtoResponse logoutResponse = gson.fromJson(jsonLogoutResponce, LogoutVoterDtoResponse.class);
        assertNull(logoutResponse.getToken());

        LoginVoterDtoRequest wrongLoginRequest = new LoginVoterDtoRequest("PrinceOfSunshine", "NiceGuy");
        String jsonWrongLoginRequest = gson.toJson(wrongLoginRequest);
        String jsonWrongLoginResponce = server.login(jsonWrongLoginRequest);
        ElectionErrors wrongLoginResponse = gson.fromJson(jsonWrongLoginResponce, ElectionErrors.class);
        assertEquals(ElectionErrorCode.WRONG_LOGIN_OR_PASSWORD.getErrorString(), wrongLoginResponse.getError());

        LoginVoterDtoRequest wrongPasswordRequest = new LoginVoterDtoRequest("PrinceOfDarkness", "BadGuy");
        String jsonWrongPasswordRequest = gson.toJson(wrongPasswordRequest);
        String jsonWrongPasswordResponce = server.login(jsonWrongPasswordRequest);
        ElectionErrors wrongPasswordResponse = gson.fromJson(jsonWrongPasswordResponce, ElectionErrors.class);
        assertEquals(ElectionErrorCode.WRONG_LOGIN_OR_PASSWORD.getErrorString(), wrongPasswordResponse.getError());
    }

    private void sortVoterList(List<Voter> list) {
        list.sort((l1, l2) -> l1.getId() - l2.getId());
    }

    private void sortCandidateList(List<CandidateList> list) {
        list.sort((l1, l2) -> l1.getId() - l2.getId());
    }

    private int getProposalAvgRatingIndex(List<ProposalAvgRating> list, String proposalTitle) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProposalTitle().equals(proposalTitle)) {
                return i;
            }
        }
        return -1;
    }
}
